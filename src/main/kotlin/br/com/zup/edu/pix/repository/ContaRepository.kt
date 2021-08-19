package br.com.zup.edu.pix.repository

import br.com.zup.edu.pix.model.Conta
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ContaRepository: JpaRepository<Conta, Long> {
}