package br.com.zup.edu.registra.repository

import br.com.zup.edu.registra.model.Conta
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ContaRepository: JpaRepository<Conta, Long> {
}