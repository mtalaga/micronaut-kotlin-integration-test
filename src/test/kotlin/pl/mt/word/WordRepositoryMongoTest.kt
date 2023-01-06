package pl.mt.word

import com.mongodb.assertions.Assertions
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@MicronautTest(startApplication=false)
class WordRepositoryMongoTest {

    @Inject
    lateinit var repository: WordRepositoryMongo

    @Test
    fun shouldStoreWordInRepository() {
        //Given
        val word = Word("hello", listOf(Translation("polish", "czesc"), Translation("deutsch", "hallo")))

        //When
        repository.putWord(word)

        //Then
        Assertions.assertNotNull(repository.findWord("hello"))
    }
}