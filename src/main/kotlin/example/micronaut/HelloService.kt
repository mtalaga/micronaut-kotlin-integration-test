package example.micronaut

import jakarta.inject.Singleton

@Singleton
class HelloService(private val helloRepository: HelloRepository) {

    fun sayHello(language: String): String? {
        return helloRepository.findHelloByLanguage(language)
    }
}