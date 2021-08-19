package br.com.zup.edu.pix.endpoints.handlersexceptions.handlers

import br.com.zup.edu.pix.endpoints.handlersexceptions.ExceptionHandler
import br.com.zup.edu.pix.service.ChavePixJaExistenteException
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