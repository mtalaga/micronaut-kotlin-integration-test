package pl.mt.hello

import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import jakarta.inject.Singleton

@Singleton
@Replaces(HelloRepositoryMongo::class)
@Requires(env = [Environment.TEST])
class HelloRepositoryInMemory: HelloRepository {

    private var helloMap: MutableMap<String, String> = HashMap()

    override fun findHelloByLanguage(language: String): String {
        return helloMap.getOrDefault(language, "Hello!")
    }

    override fun putHelloInLanguage(language: String, hello: String) {
        helloMap[language] = hello
    }

    fun clearRepository() {
        helloMap.clear()
    }
}