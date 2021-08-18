package br.com.zup.edu.pix.client.bcb

import br.com.zup.edu.pix.client.bcb.enums.KeyType

data class CreatePixKeyResponse(
    val key: String,
    val keyType: KeyType
) {

}
