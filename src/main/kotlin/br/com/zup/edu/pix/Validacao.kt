package br.com.zup.edu.pix

import br.com.zup.edu.DadosCriacaoPixRequest
import io.grpc.Status
import io.grpc.StatusRuntimeException

fun validacao(request: DadosCriacaoPixRequest?): StatusRuntimeException{
    //validacao
    var error: StatusRuntimeException = Status.OK.asRuntimeException()
    if (request?.idCliente == null || request?.idCliente.isBlank()) {
        error = Status.INVALID_ARGUMENT
            .withDescription("Id do usuário não deve ser nulo ou branco")
            .asRuntimeException()
    }
    if (request?.tipoChave!!.name.equals("CPF")) {
        if (!request?.valorChave!!.matches("^[0-9]{11}\$".toRegex())) {
            error = Status.INVALID_ARGUMENT
                .withDescription("CPF formato inválido")
                .asRuntimeException()
        }
    }
    if (request?.tipoChave!!.name.equals("CELULAR")) {
        if (!request?.valorChave!!.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())) {
            error = Status.INVALID_ARGUMENT
                .withDescription("Telefone formato inválido")
                .asRuntimeException()
        }
    }
    if (request?.tipoChave!!.name.equals("EMAIL")) {
        if (!(request?.valorChave!!.contains(".com") && request?.valorChave!!.contains("@"))) {
            error = Status.INVALID_ARGUMENT
                .withDescription("Email formato inválido")
                .asRuntimeException()
        }
    }
    if (request?.tipoChave!!.name.equals("RANDOM")) {
        if (request?.valorChave!!.isNotBlank()) {
            error = Status.INVALID_ARGUMENT
                .withDescription("Cmapo deve ser vazio para chave randomica")
                .asRuntimeException()
        }
    }
    return error
}
