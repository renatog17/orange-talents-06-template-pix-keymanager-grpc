package br.com.zup.edu.remove

import br.com.zup.edu.RemoverPixRequest
import br.com.zup.edu.RemoverPixResponse
import br.com.zup.edu.RemoverPixServiceGrpc
import io.grpc.stub.StreamObserver

class RemoverPixEndpoint() : RemoverPixServiceGrpc.RemoverPixServiceImplBase(){

    override fun remover(request: RemoverPixRequest, responseObserver: StreamObserver<RemoverPixResponse>) {

    }
}