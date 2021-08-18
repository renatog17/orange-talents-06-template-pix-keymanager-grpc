package br.com.zup.edu.pix.client.bcb

import br.com.zup.edu.pix.client.bcb.enums.PessoaType

class Owner(
    val type: PessoaType,
    val name: String, //Nome completo
    val taxIdNumber: String //CPF ou CNPJ
) {
}
