package br.com.zup.edu.client.bcb

import br.com.zup.edu.client.bcb.enums.PessoaType

data class Owner(
    val type: PessoaType,
    val name: String, //Nome completo
    val taxIdNumber: String //CPF ou CNPJ
) {
}
