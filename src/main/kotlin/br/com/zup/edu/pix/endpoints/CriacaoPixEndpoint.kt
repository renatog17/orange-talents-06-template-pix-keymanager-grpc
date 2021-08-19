package br.com.zup.edu.pix.endpoints

import br.com.zup.edu.CriacaoPixServiceGrpc
import br.com.zup.edu.DadosCriacaoPixRequest
import br.com.zup.edu.DadosCriacaoPixResponse
import br.com.zup.edu.pix.endpoints.handlersexceptions.ErrorHandler
import br.com.zup.edu.pix.extensions.toDto
import br.com.zup.edu.pix.service.NovaChavePixService
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class CriacaoPixEndpoint(
    @Inject val novaChavePixService: NovaChavePixService,

) : CriacaoPixServiceGrpc.CriacaoPixServiceImplBase() {

    override fun cadastrar(
        request: DadosCriacaoPixRequest,
        responseObserver: StreamObserver<DadosCriacaoPixResponse>
    ) {
        val dadosCriacaoPixRequestDto = request.toDto()
        val cliente = novaChavePixService.resgistra(dadosCriacaoPixRequestDto)

        responseObserver.onNext(
            DadosCriacaoPixResponse
                .newBuilder()
                .setClienteId(cliente.idCLiente)
                .setIdPix(cliente.chavePix)
                .build()
        )
        responseObserver.onCompleted()
        return
    }

}