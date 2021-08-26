package br.com.zup.edu.registra

import br.com.zup.edu.client.bcb.BcbPixClient
import br.com.zup.edu.client.erp.ErpClient
import br.com.zup.edu.registra.model.Cliente
import br.com.zup.edu.registra.repository.ClienteRepository
import io.micronaut.http.HttpStatus
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
                          @Inject val clienteRepository: ClienteRepository,
                          @Inject val bcbCliente: BcbPixClient
) {
    @Transactional
    fun resgistra(@Valid dadosNovaChavePix: DadosCriacaoPixRequestDto): Cliente {

        if(clienteRepository.existsByChavePix(dadosNovaChavePix.chave)){
            throw ChavePixJaExistenteException("Chave PIX '${dadosNovaChavePix.chave}' já existente")
        }
        val cliente:Cliente
        try {
            val contaResponse = erpClient.consultarConta(dadosNovaChavePix.tipoConta.name, dadosNovaChavePix.clienteId)
            cliente = contaResponse.body().toModel(dadosNovaChavePix)

        }catch (e:NullPointerException){
            throw IllegalStateException("Cliente não encontrado no Itau")
        }

        val bcbResponse = bcbCliente.cadastrar(cliente.toCreatePixKeyRequest())
        println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW")
        println(bcbResponse.status)
        if(bcbResponse.status == HttpStatus.UNPROCESSABLE_ENTITY){
            throw IllegalStateException("Chave PIX ${cliente.chavePix} já cadastrada no Banco Central do Brasil")
        }
        if (bcbResponse.status != HttpStatus.CREATED) // 1
            throw IllegalStateException("Erro ao registrar chave Pix no Banco Central do Brasil")


        clienteRepository.save(cliente)
        return cliente
    }
}