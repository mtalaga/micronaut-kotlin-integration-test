package pl.mt.hello

import io.micronaut.context.annotation.DefaultImplementation
import javax.validation.constraints.NotBlank

@DefaultImplementation(HelloRepositoryMongo::class)
interface HelloRepository {

    fun findHelloByLanguage(@NotBlank language: String): String?

    fun putHelloInLanguage(@NotBlank language: String, @NotBlank hello: String)
}