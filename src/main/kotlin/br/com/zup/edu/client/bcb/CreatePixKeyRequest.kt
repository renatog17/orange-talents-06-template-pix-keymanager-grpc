package br.com.zup.edu.client.bcb

import br.com.zup.edu.client.bcb.enums.KeyType

class CreatePixKeyRequest(
    val keyType: KeyType,
    val key: String,
    val bankAccount: BankAccount,
    val owner: Owner
)  {

}