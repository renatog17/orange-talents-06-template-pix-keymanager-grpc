package br.com.zup.edu.pix.service

import br.com.zup.edu.pix.client.erp.ContaResponse
import br.com.zup.edu.pix.client.erp.ErpClient
import br.com.zup.edu.pix.endpoints.dtovalid.DadosCriacaoPixRequestDto
import br.com.zup.edu.pix.model.ClienteRepository
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(@Inject val erpClient: ErpClient) {

    fun resgistra(@Valid dadosNovaChavePix: DadosCriacaoPixRequestDto):ContaResponse{
        val consultarConta = erpClient.consultarConta(dadosNovaChavePix.tipoConta.name, dadosNovaChavePix.clienteId)
        return consultarConta.body()
    }
}