package br.com.zup.edu.registra

import br.com.zup.edu.CriacaoPixServiceGrpc
import br.com.zup.edu.DadosCriacaoPixRequest
import br.com.zup.edu.DadosCriacaoPixResponse
import br.com.zup.edu.exceptions.ErrorHandler
import br.com.zup.edu.pix.extensions.toDto
import br.com.zup.edu.registra.model.Cliente
import br.com.zup.edu.registra.repository.ClienteRepository
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class CriacaoPixEndpoint(
    @Inject val novaChavePixService: NovaChavePixService
    ) : CriacaoPixServiceGrpc.CriacaoPixServiceImplBase() {

    override fun cadastrar(
        request: DadosCriacaoPixRequest,
        responseObserver: StreamObserver<DadosCriacaoPixResponse>
    ) {
        //validar
        val dadosCriacaoPixRequestDto = request.toDto()
        val cliente:Cliente
        try {
            cliente = novaChavePixService.resgistra(dadosCriacaoPixRequestDto)
        }catch (e:IllegalStateException){
            val error = Status.FAILED_PRECONDITION
                .withDescription(e.message)
                .asRuntimeException()
            responseObserver?.onError(error)
            return
        }
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