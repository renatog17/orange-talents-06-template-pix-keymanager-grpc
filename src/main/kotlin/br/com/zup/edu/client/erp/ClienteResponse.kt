package br.com.zup.edu.client.erp

data class ClienteResponse(
    val id:String,
    val nome:String,
    val cpf:String,
    val instituicao: InstituicaoResponse
) {
}