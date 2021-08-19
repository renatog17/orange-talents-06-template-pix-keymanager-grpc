package br.com.zup.edu.pix.endpoints.dtovalid

import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoConta
import br.com.zup.edu.pix.model.Cliente
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ValidPixKey
class DadosCriacaoPixRequestDto(
    @field:NotBlank val clienteId: String,
    @field:NotNull val tipoChave: TipoChave,
    @field:NotBlank @field:Size(max = 77) val chave: String,
    @field:NotNull val tipoConta: TipoConta
) {

}
