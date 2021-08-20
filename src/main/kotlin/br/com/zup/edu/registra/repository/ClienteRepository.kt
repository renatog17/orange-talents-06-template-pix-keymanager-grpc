package br.com.zup.edu.registra.repository

import br.com.zup.edu.registra.model.Cliente
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ClienteRepository: JpaRepository<Cliente, Long> {
    fun existsByChavePix(chave: String) :Boolean
}