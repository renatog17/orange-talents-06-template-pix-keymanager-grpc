package br.com.zup.edu.client.bcb

import br.com.zup.edu.client.bcb.enums.KeyType

data class CreatePixKeyResponse(
    val key: String,
    val keyType: KeyType
) {

}
