package br.com.zup.edu.pix.client.bcb

import br.com.zup.edu.pix.client.bcb.enums.AccountType

class BankAccount(
    val participant: String, //isbp
    val branch: String, //agencia
    val accountNumber: String, //numero da conta
    val accountType: AccountType //
) {
}