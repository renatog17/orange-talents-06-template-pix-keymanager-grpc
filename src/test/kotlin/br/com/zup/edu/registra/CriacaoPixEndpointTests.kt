package br.com.zup.edu.registra

import br.com.zup.edu.CriacaoPixServiceGrpc
import br.com.zup.edu.DadosCriacaoPixRequest
import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoConta
import br.com.zup.edu.client.erp.ErpClient
import br.com.zup.edu.registra.repository.ClienteRepository
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject
import org.mockito.Mockito
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class CriacaoPixEndpointTests(
    @Inject val repository: ClienteRepository,
    val grpcClient: CriacaoPixServiceGrpc.CriacaoPixServiceBlockingStub
) {

//    @Inject
//    lateinit var itauClient: ErpClient;

    @BeforeEach
    fun setUp(){
        repository.deleteAll()
    }

    @Test
    fun `deve registrar nova chave pix`(){
        println("aqui")
        val response = grpcClient.cadastrar(DadosCriacaoPixRequest.newBuilder()
            .setClienteId("c56dfef4-7901-44fb-84e2-a2cefb157890")
            .setTipoChave(TipoChave.EMAIL)
            .setChave("aaa@aaa.com")
            .setTipoConta(TipoConta.CONTA_CORRENTE)
            .build())

        with(response){
            assertEquals("c56dfef4-7901-44fb-84e2-a2cefb157890", "c56dfef4-7901-44fb-84e2-a2cefb157890")
            assertNotNull(idPix)
        }
    }

//    @MockBean(ErpClient::class)
//    fun itauClient(): ErpClient? {
//        return Mockito.mock(ErpClient::class.java)
//    }
@Factory
class Clients{
    @Bean
    fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): CriacaoPixServiceGrpc.CriacaoPixServiceBlockingStub?{
        return CriacaoPixServiceGrpc.newBlockingStub(channel)
    }
}


}