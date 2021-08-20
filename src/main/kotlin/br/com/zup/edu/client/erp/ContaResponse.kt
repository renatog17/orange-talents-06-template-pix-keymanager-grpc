package br.com.zup.edu.client.erp

import br.com.zup.edu.TipoChave
import br.com.zup.edu.registra.DadosCriacaoPixRequestDto
import br.com.zup.edu.registra.model.Cliente
import br.com.zup.edu.registra.model.Conta
import br.com.zup.edu.registra.model.Instituicao
import java.util.*

data class ContaResponse(
    val agencia: String,
    val numero: String,
    val tipo: String,
    val instituicao: InstituicaoResponse,
    val titular: TitularResponse
){

    fun toModel(chavePix: DadosCriacaoPixRequestDto): Cliente {
        val instituicao = Instituicao(
            nomeInstituicao= instituicao.nome,
            ispbInstituicao = instituicao.nome)
        val conta = Conta(
            tipoConta= chavePix.tipoConta,
            agenciaConta= agencia,
            numeroConta= numero)
        val cliente = Cliente(
            chavePix = if(chavePix.tipoChave == TipoChave.RANDOM) UUID.randomUUID().toString() else chavePix.chave,
            cpf = titular.cpf,
            idCLiente = titular.id,
            nome = titular.nome,
            instituicao = instituicao,
            conta = conta,
            tipoChave = chavePix.tipoChave
        )
        return cliente
    }
}
