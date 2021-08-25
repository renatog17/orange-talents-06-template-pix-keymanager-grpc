package br.com.zup.edu.client.erp

data class ClienteResponse(
    val id:String,
    val nome:String,
    val cpf:String,
    val instituicao: InstituicaoResponse
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClienteResponse

        if (cpf != other.cpf) return false

        return true
    }

    override fun hashCode(): Int {
        return cpf.hashCode()
    }
}