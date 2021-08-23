package br.com.zup.edu.registra

import br.com.zup.edu.*
import br.com.zup.edu.client.erp.ContaResponse
import br.com.zup.edu.client.erp.ErpClient
import br.com.zup.edu.client.erp.InstituicaoResponse
import br.com.zup.edu.client.erp.TitularResponse
import br.com.zup.edu.registra.model.Cliente
import br.com.zup.edu.registra.model.Conta
import br.com.zup.edu.registra.model.Instituicao
import br.com.zup.edu.registra.repository.ClienteRepository
import br.com.zup.edu.utils.violations
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject
import org.mockito.Mockito
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class CriacaoPixEndpointTests(
    @Inject val repository: ClienteRepository,
    val grpcClient: CriacaoPixServiceGrpc.CriacaoPixServiceBlockingStub
) {

    companion object {
        val CLIENTE_ID = "c56dfef4-7901-44fb-84e2-a2cefb157890"
    }

    @Inject
    lateinit var itauClient: ErpClient;

    @BeforeEach
    fun setUp(){
        repository.deleteAll()
    }

    @Test
    fun `deve registrar nova chave pix`(){
        `when`(itauClient?.consultarConta("CONTA_CORRENTE", CLIENTE_ID))
            .thenReturn(HttpResponse.ok(contaResponse()))
        val response = grpcClient.cadastrar(DadosCriacaoPixRequest.newBuilder()
            .setClienteId(CLIENTE_ID)
            .setTipoChave(TipoChave.EMAIL)
            .setChave("rafael@email.com")
            .setTipoConta(TipoConta.CONTA_CORRENTE)
            .build())

        with(response){
            assertEquals(CLIENTE_ID, CLIENTE_ID)
            assertNotNull(idPix)
        }
    }

    @Test
    fun `nao deve registrar nova chave pix duplicada`(){
        //cenário
        repository.save(Cliente(
            tipoChave = TipoChave.EMAIL,
            chavePix = "renato@email.com",
            idCLiente = CLIENTE_ID,
            instituicao = Instituicao("ITAÚ UNIBANCO S.A.", "60701190"),
            cpf = "02467781054",
            nome = "Rafael M C Ponte",
            conta = Conta(agenciaConta = "0001", numeroConta = "291900", tipoConta = TipoConta.CONTA_CORRENTE)
        ))
        //ação
        val thrown = assertThrows<StatusRuntimeException> {
            val response = grpcClient.cadastrar(DadosCriacaoPixRequest.newBuilder()
                .setClienteId(CLIENTE_ID)
                .setTipoChave(TipoChave.EMAIL)
                .setChave("renato@email.com")
                .setTipoConta(TipoConta.CONTA_CORRENTE)
                .build()
            )
        }
        //validação
        with(thrown){
            assertEquals(Status.ALREADY_EXISTS.code, status.code)
            assertEquals("Chave PIX 'renato@email.com' já existente", status.description)
        }
    }

    @Test
    fun `nao deve registrar nova chave quando nao encontrar dados na conta do cliente`(){
        //cenário
        `when`(itauClient?.consultarConta(TipoConta.CONTA_CORRENTE.name, CLIENTE_ID))
            .thenReturn(HttpResponse.notFound())
        //ação
        val thrown = assertThrows<StatusRuntimeException> {
                        grpcClient.cadastrar(DadosCriacaoPixRequest.newBuilder()
                            .setClienteId(CLIENTE_ID)
                            .setTipoChave(TipoChave.EMAIL)
                            .setChave("rafael@email.com")
                            .setTipoConta(TipoConta.CONTA_CORRENTE)
                            .build()
                        )
        }
        println("${thrown.status.code} aaaaa")
        //validação
        with(thrown){
            assertEquals(Status.FAILED_PRECONDITION.code, status.code)
            assertEquals("Cliente não encontrado no Itau", status.description)
        }
    }

//    @Test
//    fun `nao deve registrar nova chave quando parametos forem invalidos`(){
//        //acao
//        val thrown = assertThrows<StatusRuntimeException> {
//            grpcClient.cadastrar(DadosCriacaoPixRequest.newBuilder().build())
//        }
//
//        //validacao
//        with(thrown){
//            assertEquals(Status.INVALID_ARGUMENT.code, status.code)
//            assertEquals("Dados inválidos", status.description)
//            assertThat(violations(), containsInAnyOrder(
//                Pair("clienteId", "must not be blank"),
//                Pair("tipoDeConta", "must not be null"),
//                Pair("tipo", "must not be null"),))
//        }
//    }

    @MockBean(ErpClient::class)
    fun itauClient(): ErpClient? {
        return Mockito.mock(ErpClient::class.java)
    }
@Factory
class Clients{
    @Singleton
    fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): CriacaoPixServiceGrpc.CriacaoPixServiceBlockingStub?{
        return CriacaoPixServiceGrpc.newBlockingStub(channel)
    }
}

    private fun contaResponse(): ContaResponse{
        return ContaResponse(
            agencia = "0001",
            numero = "291900",
            titular = TitularResponse(id = CLIENTE_ID, nome="Rafael M C Ponte", cpf="02467781054"),
            tipo = "CONTA_CORRENTE",
            instituicao = InstituicaoResponse(nome ="ITAÚ UNIBANCO S.A.", ispb ="60701190")
        )
    }

}