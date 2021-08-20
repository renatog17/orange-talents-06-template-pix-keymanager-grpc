package br.com.zup.edu.registra.model

import br.com.zup.edu.TipoConta
import io.micronaut.core.annotation.Introspected
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Embeddable
class Conta(
    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val tipoConta: TipoConta,
    @field:NotBlank
    @field:Size(max = 4)
    @Column(length = 4, nullable = false)
    val agenciaConta: String,
    @field:NotBlank
    @field:Size(max = 6)
    @Column(length = 6, nullable = false)
    val numeroConta:String
) {
}