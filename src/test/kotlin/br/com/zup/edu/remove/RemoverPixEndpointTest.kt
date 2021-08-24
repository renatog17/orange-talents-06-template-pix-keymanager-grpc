package br.com.zup.edu.remove

import br.com.zup.edu.CriacaoPixServiceGrpc
import br.com.zup.edu.registra.repository.ClienteRepository
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class RemoverPixEndpointTest(
    @Inject val repository: ClienteRepository,
    val grpcClient: CriacaoPixServiceGrpc.CriacaoPixServiceBlockingStub
) {



    @Factory
    class Clients{
        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): CriacaoPixServiceGrpc.CriacaoPixServiceBlockingStub?{
            return CriacaoPixServiceGrpc.newBlockingStub(channel)
        }
    }
}