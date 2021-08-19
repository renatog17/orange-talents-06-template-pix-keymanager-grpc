package br.com.zup.edu.pix.extensions

import br.com.zup.edu.DadosCriacaoPixRequest
import br.com.zup.edu.pix.endpoints.dtovalid.DadosCriacaoPixRequestDto

fun DadosCriacaoPixRequest.toDto() : DadosCriacaoPixRequestDto {

    return DadosCriacaoPixRequestDto(
        clienteId = clienteId,
        tipoChave = tipoChave.name,
        chave = chave,
        tipoConta = tipoConta.name)
}