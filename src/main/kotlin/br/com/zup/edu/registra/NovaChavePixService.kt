package br.com.zup.edu.registra

import br.com.zup.edu.client.erp.ErpClient
import br.com.zup.edu.registra.model.Cliente
import br.com.zup.edu.registra.repository.ClienteRepository
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(@Inject val erpClient: ErpClient,
                          @Inject val clienteRepository: ClienteRepository
) {
    @Transactional
    fun resgistra(@Valid dadosNovaChavePix: DadosCriacaoPixRequestDto): Cliente {

        if(clienteRepository.existsByChavePix(dadosNovaChavePix.chave)){
            throw ChavePixJaExistenteException("Chave PIX '${dadosNovaChavePix.chave}' já existente")
        }

        val contaResponse = erpClient.consultarConta(dadosNovaChavePix.tipoConta.name, dadosNovaChavePix.clienteId)
        val cliente = contaResponse.body()?.toModel(dadosNovaChavePix) ?: throw IllegalStateException("Cliente não encontrado no Itau")
        clienteRepository.save(cliente)
        return cliente
    }
}