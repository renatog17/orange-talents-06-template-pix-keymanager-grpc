package br.com.zup.edu.pix

import br.com.zup.edu.CriacaoPixServiceGrpc
import br.com.zup.edu.DadosCriacaoPixRequest
import br.com.zup.edu.DadosCriacaoPixResponse
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
class CriacaoPixEndpoint:CriacaoPixServiceGrpc.CriacaoPixServiceImplBase(){

    override fun cadastrar(
        request: DadosCriacaoPixRequest?,
        responseObserver: StreamObserver<DadosCriacaoPixResponse>?
    ) {
        println(request?.idCliente)
        responseObserver?.onNext(DadosCriacaoPixResponse
                .newBuilder()
                .setMessage("Requisição feita com sucesso")
                .build())
        responseObserver?.onCompleted()
        return
    }

}