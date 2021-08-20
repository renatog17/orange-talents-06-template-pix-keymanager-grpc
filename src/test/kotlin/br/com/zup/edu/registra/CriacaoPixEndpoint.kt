package br.com.zup.edu.registra

import br.com.zup.edu.registra.repository.ClienteRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.BeforeEach

@MicronautTest(transactional = false)
internal class CriacaoPixEndpoint(
    val repository: ClienteRepository
) {

    @BeforeEach
    fun setUp(){
        repository.deleteAll()
    }
}