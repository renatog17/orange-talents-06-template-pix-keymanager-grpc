package br.com.zup.edu.exceptions.handlers

import br.com.zup.edu.exceptions.ExceptionHandler
import br.com.zup.edu.remove.PermissaoNegadaRemoverChavePixException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class PermissaoNegadaRemoverChavePixHandler : ExceptionHandler<PermissaoNegadaRemoverChavePixException> {

    override fun handle(e: PermissaoNegadaRemoverChavePixException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.PERMISSION_DENIED
                .withDescription(e.message)
                .withCause(e)
        )
    }

    override fun supports(e: Exception): Boolean {
        return e is PermissaoNegadaRemoverChavePixException
    }
}