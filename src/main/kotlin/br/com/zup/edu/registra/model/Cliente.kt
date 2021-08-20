package br.com.zup.edu.registra.model

import io.micronaut.core.annotation.Introspected
import javax.persistence.*

@Introspected
@Entity
class Cliente(
    val chavePix: String,
    val cpf: String,
    val idCLiente: String,
    val nome: String,
    @ManyToOne
    val instituicao: Instituicao,
    @ManyToOne
    val conta: Conta
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}