package br.com.zup.edu.pix.service

import br.com.zup.edu.pix.client.erp.ErpClient
import br.com.zup.edu.pix.endpoints.dtovalid.DadosCriacaoPixRequestDto
import br.com.zup.edu.pix.model.Cliente
import br.com.zup.edu.pix.repository.ClienteRepository
import br.com.zup.edu.pix.repository.ContaRepository
import br.com.zup.edu.pix.repository.InstituicaoRepository
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(@Inject val erpClient: ErpClient,
                          @Inject val clienteRepository: ClienteRepository,
                          @Inject val instituicaoRepository: InstituicaoRepository,
                          @Inject val contaRepository: ContaRepository
) {

    fun resgistra(@Valid dadosNovaChavePix: DadosCriacaoPixRequestDto):Cliente{
        val contaResponse = erpClient.consultarConta(dadosNovaChavePix.tipoConta.name, dadosNovaChavePix.clienteId)
        if(clienteRepository.existsByChavePix(dadosNovaChavePix.chave)){
            throw ChavePixJaExistenteException("Chave PIX '{${dadosNovaChavePix.chave}' já existete")
        }
        val cliente = contaResponse.body().toModel(dadosNovaChavePix.chave)
        contaRepository.save(cliente.conta)
        instituicaoRepository.save(cliente.instituicao)
        clienteRepository.save(cliente)
        return cliente
    }
}