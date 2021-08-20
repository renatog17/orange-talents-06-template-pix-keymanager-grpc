package br.com.zup.edu.client.bcb

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:8082")
interface BcbPixClient {

    @Post("/api/v1/pix/keys", consumes = [MediaType.APPLICATION_XML],
    produces = [MediaType.APPLICATION_XML])
    fun cadastrar(@Body CreatePixKeyRequest: CreatePixKeyRequest) : HttpResponse<CreatePixKeyResponse>
}