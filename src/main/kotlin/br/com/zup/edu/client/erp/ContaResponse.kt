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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContaResponse

        if (agencia != other.agencia) return false
        if (numero != other.numero) return false
        if (tipo != other.tipo) return false
        if (instituicao != other.instituicao) return false
        if (titular != other.titular) return false

        return true
    }

    override fun hashCode(): Int {
        var result = agencia.hashCode()
        result = 31 * result + numero.hashCode()
        result = 31 * result + tipo.hashCode()
        result = 31 * result + instituicao.hashCode()
        result = 31 * result + titular.hashCode()
        return result
    }
}
