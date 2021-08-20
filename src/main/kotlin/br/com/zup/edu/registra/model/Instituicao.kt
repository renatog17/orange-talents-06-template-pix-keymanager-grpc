package br.com.zup.edu.registra.model

import io.micronaut.core.annotation.Introspected
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Embeddable
class Instituicao(
    @field:NotBlank
    @Column(nullable = false)
    val nomeInstituicao: String,
    @field:NotBlank
    @Column(nullable = false)
    val ispbInstituicao: String
) {
}