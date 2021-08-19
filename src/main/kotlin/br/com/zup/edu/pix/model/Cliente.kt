package br.com.zup.edu.pix.model

import br.com.zup.edu.pix.client.erp.InstituicaoResponse
import br.com.zup.edu.pix.client.erp.TitularResponse
import io.micronaut.core.annotation.Generated
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