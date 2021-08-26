package br.com.zup.edu.client.bcb

import br.com.zup.edu.client.bcb.enums.AccountType

data class BankAccount(
    val participant: String, //isbp
    val branch: String, //agencia
    val accountNumber: String, //numero da conta
    val accountType: AccountType //
) {
}