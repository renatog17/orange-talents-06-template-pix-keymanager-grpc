package br.com.zup.edu.client.bcb

import br.com.zup.edu.client.bcb.enums.KeyType

data class CreatePixKeyResponse(
    val key: String,
    val keyType: KeyType
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreatePixKeyResponse

        if (keyType != other.keyType) return false

        return true
    }

    override fun hashCode(): Int {
        return keyType.hashCode()
    }
}
