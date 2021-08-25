package br.com.zup.edu.remove

import br.com.zup.edu.*
import br.com.zup.edu.client.erp.*
import br.com.zup.edu.registra.CriacaoPixEndpointTests
import br.com.zup.edu.registra.model.Cliente
import br.com.zup.edu.registra.model.Conta
import br.com.zup.edu.registra.model.Instituicao
import br.com.zup.edu.registra.repository.ClienteRepository
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class RemoverPixEndpointTest(
    @Inject val repository: ClienteRepository,
    val grpcClient: RemoverPixServiceGrpc.RemoverPixServiceBlockingStub
) {
    companion object {
        val CLIENTE_ID = "c56dfef4-7901-44fb-84e2-a2cefb157890"
    }

    @Inject
    lateinit var itauClient: ErpClient;

    @Test
    fun `nao deve excluir chave quando cliente nao for encontrado no itau`(){
        //cenario
        `when`(itauClient.consultarCliente(CLIENTE_ID))
            .thenReturn(HttpResponse.notFound())
        //acao
        val thrown = assertThrows<StatusRuntimeException> {
            grpcClient.remover(
                RemoverPixRequest
                .newBuilder()
                .setClienteId(CLIENTE_ID)
                .setPixId("chave")
                .build())
        }
        //validacao
        with(thrown){
            assertEquals(Status.ABORTED.code, status.code)
            assertEquals("Cliente não encontrado no banco Itaú", status.description)
        }
    }

    @Test
    fun `não deve excluir chave quando chave pix nao for encontrada`(){
        `when`(itauClient.consultarCliente(CLIENTE_ID))
            .thenReturn(HttpResponse.ok(clienteResponse()))

        val thrown = assertThrows<StatusRuntimeException> {
            grpcClient.remover(RemoverPixRequest
                .newBuilder()
                .setClienteId(CLIENTE_ID)
                .setPixId("cahve")
                .build()
            )
        }
        with(thrown){
            assertEquals(Status.NOT_FOUND.code, status.code)
            assertEquals("Chave pix não encontrada", status.description)
        }
    }

    //erro nesse teste
    @Test
    fun `nao deve excluir chave quando usuario nao for proprietario`(){
        //cenario
        `when`(itauClient.consultarCliente(CLIENTE_ID))
            .thenReturn(HttpResponse.ok(clienteResponse()))
        val cliente = Cliente(
            chavePix="teste@email.com",
            tipoChave = TipoChave.EMAIL,
            cpf ="02467781054",
            idCLiente = CLIENTE_ID,
            nome ="Rafael M C Ponte",
            instituicao= Instituicao(nomeInstituicao="ITAÚ UNIBANCO S.A.", ispbInstituicao="60701190"),
            conta= Conta(agenciaConta = "0001", numeroConta = "291900", tipoConta = TipoConta.CONTA_CORRENTE)
        )
        repository.save(cliente)
        //acao
        val thrown = assertThrows<StatusRuntimeException> {
            grpcClient.remover(RemoverPixRequest
                .newBuilder()
                .setClienteId("0d1bb194-3c52-4e67-8c35-a93c0af9284f")
                .setPixId("teste@email.com")
                .build())
        }

        //validacao
        with(thrown) {
            assertEquals(Status.PERMISSION_DENIED.code, status.code)
            assertEquals("Usuário não tem permissão para excluir essa chave", status.description)
        }
    }
    @MockBean(ErpClient::class)
    fun itauClient(): ErpClient? {
        return Mockito.mock(ErpClient::class.java)
    }



    @Factory
    class Clients{
        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): RemoverPixServiceGrpc.RemoverPixServiceBlockingStub?{
            return RemoverPixServiceGrpc.newBlockingStub(channel)
        }
    }

    private fun clienteResponse(): ClienteResponse {
        return ClienteResponse(
            id= CLIENTE_ID,
            nome="Rafael M C Ponte",
            cpf="02467781054",
            instituicao = InstituicaoResponse(nome ="ITAÚ UNIBANCO S.A.", ispb ="60701190")
        )
    }
}