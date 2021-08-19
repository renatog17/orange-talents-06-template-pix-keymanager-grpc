package br.com.zup.edu.pix.endpoints.dtovalid

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.TYPE
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass

@MustBeDocumented
@Target(CLASS, TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = [ValidPixKeyValidator::class])
annotation class ValidPixKey (
    val message: String = "Chave PIX inválida",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)

@Singleton
class ValidPixKeyValidator :ConstraintValidator<ValidPixKey, DadosCriacaoPixRequestDto>{
    override fun isValid(
        value: DadosCriacaoPixRequestDto?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(value?.tipoChave==null){
            return false
        }
        val chave = value.chave
        val tipoChave = value.tipoChave
        if(chave.isNullOrBlank()){
            return false
        }
        if(tipoChave.number==1){
            if(chave.isNotBlank()) return false
        }
        //CELULAR
        if(tipoChave.number==2){
            if(!chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())) return false

        }
        //EMAIL
        if(tipoChave.number==3){
            return (chave.contains("@") && chave.contains(".com"))
        }
        //CPF
        if(tipoChave.number==4){
            if(!chave.matches("^[0-9]{11}\$".toRegex())) return false
            if(!isCPF(chave)) return false
        }
        return true
    }

}
