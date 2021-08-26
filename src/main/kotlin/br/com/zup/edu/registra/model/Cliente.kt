package br.com.zup.edu.registra.model

import br.com.zup.edu.TipoChave
import br.com.zup.edu.client.bcb.BankAccount
import br.com.zup.edu.client.bcb.CreatePixKeyRequest
import br.com.zup.edu.client.bcb.Owner
import br.com.zup.edu.client.bcb.enums.AccountType
import br.com.zup.edu.client.bcb.enums.KeyType
import br.com.zup.edu.client.bcb.enums.PessoaType
import io.micronaut.core.annotation.Introspected
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@Entity
class Cliente(
    @field:NotBlank
    @Column(unique = true, nullable = false)
    val chavePix: String,
    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipoChave: TipoChave,
    @field:NotBlank
    @field:Size(max = 11)
    @Column(length = 11, nullable = false)
    val cpf: String,
    @field:NotNull
    @Column(nullable = false)
    val idCLiente: String,
    @field:NotBlank
    @Column(nullable = false)
    val nome: String,
    @Embedded
    val instituicao: Instituicao,
    @Embedded
    val conta: Conta
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun toCreatePixKeyRequest(): CreatePixKeyRequest {
        var keyType: KeyType = KeyType.RANDOM
        if(tipoChave.number==1) keyType = KeyType.PHONE
        if(tipoChave.number==2) keyType = KeyType.EMAIL
        if(tipoChave.number==3) keyType = KeyType.CPF

        var accountType:AccountType = AccountType.CACC
        if(conta.tipoConta.number==2) accountType = AccountType.CACC
        val bankAccount = BankAccount(
            participant = instituicao.ispbInstituicao,
            branch = conta.agenciaConta,
            accountNumber = conta.numeroConta,
            accountType = accountType
         )

        val owner=Owner(
            type = PessoaType.NATURAL_PERSON,
            name = nome,
            taxIdNumber= idCLiente
        )
        val createPixKeyRequest=CreatePixKeyRequest(
            keyType = keyType,
            key =chavePix,
            bankAccount= bankAccount,
            owner= owner
        )
        return createPixKeyRequest
    }
}