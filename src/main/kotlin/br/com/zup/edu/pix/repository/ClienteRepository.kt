package br.com.zup.edu.pix.repository

import br.com.zup.edu.pix.model.Cliente
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ClienteRepository: JpaRepository<Cliente, Long> {
    fun existsByChavePix(chave: String) :Boolean
}