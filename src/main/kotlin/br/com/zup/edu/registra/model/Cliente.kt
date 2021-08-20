package br.com.zup.edu.registra.model

import br.com.zup.edu.TipoChave
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
}