package br.com.zup.edu.remove

import br.com.zup.edu.RemoverPixRequest
import br.com.zup.edu.RemoverPixResponse
import br.com.zup.edu.RemoverPixServiceGrpc
import br.com.zup.edu.exceptions.ErrorHandler
import br.com.zup.edu.registra.model.Cliente
import br.com.zup.edu.registra.repository.ClienteRepository
import io.grpc.stub.StreamObserver
import io.grpc.Status
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ErrorHandler
class RemoverPixEndpoint(
    @Inject val clienteRepository: ClienteRepository
    ) : RemoverPixServiceGrpc.RemoverPixServiceImplBase(){

    override fun remover(
        request: RemoverPixRequest,
        responseObserver: StreamObserver<RemoverPixResponse>
    ) {
        if(!clienteRepository.existsByChavePix(request.pixId)){
            throw ChavePixNaoEncontradaException("Chave pix não encontrada")
        }
        val cliente:Cliente = clienteRepository.findByChavePix(request.pixId)
        if(request.clienteId.equals(cliente.idCLiente)) {
            clienteRepository.deleteByChavePix(request.pixId)
            responseObserver.onNext(
                RemoverPixResponse.newBuilder()
                    .setPixId(cliente.chavePix)
                    .build()
            )
            responseObserver.onCompleted()
            return
        }else{
            throw PermissaoNegadaRemoverChavePixException("Usuário não tem permissão para excluir essa chave")
        }

    }
}