package br.com.zup.edu.pix.client.erp

data class ContaResponse(
    val agencia: String,
    val numero: String,
    val tipo: String,
    val instituicao: InstituicaoResponse,
    val titular: TitularResponse
){

}
