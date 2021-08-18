package br.com.zup.edu.pix.model

import io.micronaut.core.annotation.Generated
import io.micronaut.core.annotation.Introspected
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Introspected
@Entity
class Cliente(
    val chavePix: String,
    val tipo: String,
    val idCLiente: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}