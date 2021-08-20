package br.com.zup.edu.exceptions.handlers

import br.com.zup.edu.exceptions.ExceptionHandler
import br.com.zup.edu.registra.ChavePixJaExistenteException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class ChavePixExistenteExceptionHandler : ExceptionHandler<ChavePixJaExistenteException> {

    override fun handle(e: ChavePixJaExistenteException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.ALREADY_EXISTS
                .withDescription(e.message)
                .withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is ChavePixJaExistenteException
    }
}