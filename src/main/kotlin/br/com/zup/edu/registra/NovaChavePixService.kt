package br.com.zup.edu.registra

import br.com.zup.edu.client.erp.ErpClient
import br.com.zup.edu.registra.model.Cliente
import br.com.zup.edu.registra.repository.ClienteRepository
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.validation.Validated
import jdk.swing.interop.SwingInterOpUtils
import java.lang.NullPointerException
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
        try {
            val contaResponse = erpClient.consultarConta(dadosNovaChavePix.tipoConta.name, dadosNovaChavePix.clienteId)
            val cliente = contaResponse.body().toModel(dadosNovaChavePix)
            clienteRepository.save(cliente)
            return cliente
        }catch (e:NullPointerException){
            throw IllegalStateException("Cliente não encontrado no Itau")
        }
    }
}