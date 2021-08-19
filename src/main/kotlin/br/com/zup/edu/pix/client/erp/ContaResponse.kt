package br.com.zup.edu.pix.client.erp

import br.com.zup.edu.pix.model.Cliente
import br.com.zup.edu.pix.model.Conta
import br.com.zup.edu.pix.model.Instituicao

data class ContaResponse(
    val agencia: String,
    val numero: String,
    val tipo: String,
    val instituicao: InstituicaoResponse,
    val titular: TitularResponse
){

    fun toModel():Cliente{
        val instituicao = Instituicao(nome=instituicao.nome, ispb = instituicao.nome)
        val conta = Conta(tipo=tipo,agencia=agencia, numero=numero)
        val cliente = Cliente(
            chavePix = "chave teste",
            cpf = titular.cpf,
            idCLiente = titular.id,
            nome = titular.nome,
            instituicao = instituicao,
            conta = conta
        )
        return cliente
    }
}
