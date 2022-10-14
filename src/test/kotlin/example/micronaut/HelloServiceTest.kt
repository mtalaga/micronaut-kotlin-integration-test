package example.micronaut

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest(startApplication=false)
class HelloServiceTest {
    @Inject
    lateinit var helloRepository: HelloRepositoryInMemory

    @Inject
    lateinit var helloService: HelloService

    @AfterEach
    fun afterEach() {
        helloRepository.clearRepository()
    }

    @Test
    fun shouldReturnHello_whenHelloIsInRepository() {
        //Given
        val polishHello = "Czesc!"
        helloRepository.putHelloInLanguage("polish", polishHello)

        //When
        val result = helloService.sayHello("polish")

        //Then
        Assertions.assertEquals(result, polishHello)
    }

    @Test
    fun shouldReturnDefaultHello_ifHelloNotInRepository() {
        //When
        val result = helloService.sayHello("polish")

        //Then
        Assertions.assertEquals(result, "Hello!")
    }
}