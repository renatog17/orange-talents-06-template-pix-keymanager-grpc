package br.com.zup.edu.pix.client.erp

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:9091/api/v1")
interface ErpClient {

    @Get("/clientes/{clienteId}/contas")
    fun consultarConta(@QueryValue tipo:String, clienteId:String?): HttpResponse<ContaResponse>
}