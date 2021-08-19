package br.com.zup.edu.pix.model

import io.micronaut.core.annotation.Introspected
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Introspected
@Entity
class Conta(
    val tipo: String,
    val agencia: String,
    val numero:String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}