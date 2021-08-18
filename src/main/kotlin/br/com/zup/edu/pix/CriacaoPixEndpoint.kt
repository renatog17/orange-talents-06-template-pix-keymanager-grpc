package br.com.zup.edu.pix

import br.com.zup.edu.CriacaoPixServiceGrpc
import br.com.zup.edu.DadosCriacaoPixRequest
import br.com.zup.edu.DadosCriacaoPixResponse
import br.com.zup.edu.pix.client.bcb.*
import br.com.zup.edu.pix.client.erp.ContaResponse
import br.com.zup.edu.pix.client.erp.ErpClient
import br.com.zup.edu.pix.model.Cliente
import br.com.zup.edu.pix.model.ClienteRepository
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.http.client.exceptions.HttpClientResponseException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CriacaoPixEndpoint(
    val erpClient: ErpClient,
    val bcbPixClient: BcbPixClient,
    @Inject val clienteRepository: ClienteRepository
) : CriacaoPixServiceGrpc.CriacaoPixServiceImplBase() {

    override fun cadastrar(
        request: DadosCriacaoPixRequest?,
        responseObserver: StreamObserver<DadosCriacaoPixResponse>?
    ) {
        //validação
        val error = validacao(request)
        if(!error.status.isOk){
            println("deu tudo errado")
            responseObserver?.onError(error)
        }
        //pegar dados da conta erp
        var contaResponse : ContaResponse
        try {
            println(println(request?.tipoConta!!.name))
            contaResponse = erpClient.consultarConta(request?.tipoConta!!.name, request?.idCliente).body()
        }catch (e: HttpClientResponseException){
            responseObserver?.onError(Status.NOT_FOUND
                .withDescription("Id formato inválido. Conta não contrada")
                .asRuntimeException())
            responseObserver?.onCompleted()
        }catch (e: NullPointerException){
            responseObserver?.onError(Status.NOT_FOUND
                .withDescription("Não existe esse tipo de conta para este usuário")
                .asRuntimeException())
            responseObserver?.onCompleted()
        }
//        //montar request bcb
//        val montaRequest = MontaCreatePixKeyRequest()
//        val createPixKeyRequest = montaRequest.montarRequest(contaResponse, request)
//        //criar chave no sistema bcb
//        val createPixKeyResponse = bcbPixClient.cadastrar(createPixKeyRequest)
//
//        val cliente = Cliente(createPixKeyResponse.body().key, createPixKeyResponse.body().keyType.name, contaResponse.titular.id)
//        clienteRepository.save(cliente)
        responseObserver?.onNext(
            DadosCriacaoPixResponse
                .newBuilder()
                .setMessage("Requisição feita com sucesso")
                .build()
        )
        responseObserver?.onCompleted()
        return
    }

}