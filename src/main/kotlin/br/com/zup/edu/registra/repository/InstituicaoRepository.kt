package br.com.zup.edu.registra.repository

import br.com.zup.edu.registra.model.Instituicao
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface InstituicaoRepository: JpaRepository<Instituicao, Long> {
}