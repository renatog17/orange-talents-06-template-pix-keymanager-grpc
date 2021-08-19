package br.com.zup.edu.pix.endpoints

import br.com.zup.edu.CriacaoPixServiceGrpc
import br.com.zup.edu.DadosCriacaoPixRequest
import br.com.zup.edu.DadosCriacaoPixResponse
import br.com.zup.edu.pix.client.bcb.*
import br.com.zup.edu.pix.client.erp.ContaResponse
import br.com.zup.edu.pix.client.erp.ErpClient
import br.com.zup.edu.pix.extensions.toDto
import br.com.zup.edu.pix.model.ClienteRepository
import br.com.zup.edu.pix.model.ContaRepository
import br.com.zup.edu.pix.model.InstituicaoRepository
import br.com.zup.edu.pix.service.NovaChavePixService
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.http.client.exceptions.HttpClientResponseException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CriacaoPixEndpoint(
    @Inject val novaChavePixService: NovaChavePixService,
    @Inject val clienteRepository: ClienteRepository,
    @Inject val instituicaoRepository: InstituicaoRepository,
@Inject val contaRepository: ContaRepository
) : CriacaoPixServiceGrpc.CriacaoPixServiceImplBase() {

    override fun cadastrar(
        request: DadosCriacaoPixRequest?,
        responseObserver: StreamObserver<DadosCriacaoPixResponse>?
    ) {
        val dadosCriacaoPixRequestDto = request?.toDto()
        val contaResponse = novaChavePixService.resgistra(dadosCriacaoPixRequestDto!!)
        val cliente = contaResponse.toModel()

        contaRepository.save(cliente.conta)
        instituicaoRepository.save(cliente.instituicao)
        clienteRepository.save(cliente)
        responseObserver?.onNext(
            DadosCriacaoPixResponse
                .newBuilder()
                .setIdPix("123")
                .build()
        )
        responseObserver?.onCompleted()
        return
    }

}