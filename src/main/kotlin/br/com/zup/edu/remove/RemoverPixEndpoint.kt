package br.com.zup.edu.remove

import br.com.zup.edu.RemoverPixRequest
import br.com.zup.edu.RemoverPixResponse
import br.com.zup.edu.RemoverPixServiceGrpc
import br.com.zup.edu.client.erp.ContaResponse
import br.com.zup.edu.client.erp.ErpClient
import br.com.zup.edu.exceptions.ErrorHandler
import br.com.zup.edu.registra.model.Cliente
import br.com.zup.edu.registra.repository.ClienteRepository
import io.grpc.stub.StreamObserver
import io.grpc.Status
import io.micronaut.http.client.exceptions.HttpClientResponseException
import java.lang.NullPointerException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ErrorHandler
class RemoverPixEndpoint(
    @Inject val clienteRepository: ClienteRepository,
    @Inject val erpClient: ErpClient
    ) : RemoverPixServiceGrpc.RemoverPixServiceImplBase(){

    override fun remover(
        request: RemoverPixRequest,
        responseObserver: StreamObserver<RemoverPixResponse>
    ) {


        val clienteResponse = erpClient.consultarCliente(request.clienteId)
        if(clienteResponse.status().code==404){
            throw ClienteItauNaoEncontradoException("Cliente não encontrado no banco Itaú")
        }

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