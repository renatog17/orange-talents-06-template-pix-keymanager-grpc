package br.com.zup.edu.pix.client.bcb

import br.com.zup.edu.DadosCriacaoPixRequest
import br.com.zup.edu.pix.client.bcb.enums.KeyType
import br.com.zup.edu.pix.client.bcb.enums.PessoaType
import br.com.zup.edu.pix.client.erp.ContaResponse
import br.com.zup.edu.pix.client.bcb.enums.AccountType

class MontaCreatePixKeyRequest {

    fun montarRequest(contaResponse: ContaResponse, dadosCriacaoPixRequest: DadosCriacaoPixRequest?):CreatePixKeyRequest{
        var accountType:AccountType = AccountType.SVGS
        if(contaResponse.tipo.equals("CONTA_CORRENTE")){
            accountType = AccountType.CACC
        }
        val bankAccount = BankAccount(contaResponse.instituicao.ispb,contaResponse.agencia,contaResponse.numero, accountType)

        val owner = Owner(PessoaType.NATURAL_PERSON,contaResponse.titular.nome,contaResponse.titular.cpf )

        var keyType:KeyType = KeyType.RANDOM
        if(dadosCriacaoPixRequest!!.tipoChave.number==1)
            keyType = KeyType.PHONE
        if(dadosCriacaoPixRequest!!.tipoChave.number==2)
            keyType = KeyType.EMAIL
        if(dadosCriacaoPixRequest!!.tipoChave.number==3)
            keyType = KeyType.CPF
        val createPixKeyRequest = CreatePixKeyRequest( keyType,contaResponse.titular.cpf, bankAccount, owner );
        return createPixKeyRequest
    }
}