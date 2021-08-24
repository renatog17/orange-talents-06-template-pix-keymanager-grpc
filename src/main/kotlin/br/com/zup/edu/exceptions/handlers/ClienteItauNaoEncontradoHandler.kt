package br.com.zup.edu.exceptions.handlers

import br.com.zup.edu.exceptions.ExceptionHandler
import br.com.zup.edu.remove.ClienteItauNaoEncontradoException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class ClienteItauNaoEncontradoHandler : ExceptionHandler<ClienteItauNaoEncontradoException> {

    override fun handle(e: ClienteItauNaoEncontradoException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.ABORTED
                .withDescription(e.message)
                .withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is ClienteItauNaoEncontradoException
    }
}