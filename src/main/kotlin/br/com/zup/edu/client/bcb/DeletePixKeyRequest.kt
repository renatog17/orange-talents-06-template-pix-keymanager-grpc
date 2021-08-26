package br.com.zup.edu.client.bcb

data class DeletePixKeyRequest(
    val key: String,
    val participant: String
) {
}