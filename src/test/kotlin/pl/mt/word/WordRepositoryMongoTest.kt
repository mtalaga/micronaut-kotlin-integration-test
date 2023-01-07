package pl.mt.word

import com.mongodb.assertions.Assertions
import com.mongodb.client.MongoClient
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.bson.Document
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest(startApplication = false)
class WordRepositoryMongoTest {

    @field:Property(name = "word.database")
    var databaseName: String = ""

    @field:Property(name = "word.collection")
    var collectionName: String = ""

    @Inject
    lateinit var repository: WordRepositoryMongo

    @Inject
    lateinit var mongoClient: MongoClient

    @BeforeEach
    fun beforeEach() {
        mongoClient.getDatabase(databaseName)
            .getCollection(collectionName)
            .deleteMany(Document())
    }

    @Test
    fun shouldStoreWordInRepository() {
        //Given
        val word = Word(
            "hello", listOf(
                Translation("polish", "czesc"),
                Translation("deutsch", "hallo")
            )
        )

        //When
        repository.putWord(word)

        //Then
        val wordFromRepository = repository.findWord("hello")
        Assertions.assertTrue(wordFromRepository != null)
        Assertions.assertTrue(wordFromRepository!!.translations.size == 2)
        Assertions.assertTrue(wordFromRepository!!.translations
            .filter { it.language == "polish" && it.translation == "czesc" }
            .size == 1)
        Assertions.assertTrue(wordFromRepository!!.translations
            .filter { it.language == "deutsch" && it.translation == "hallo" }
            .size == 1)
    }

    @Test
    fun shouldReturnEmpty_whenWordNotInRepository() {
        //When
        val result = repository.findWord("hello")

        //Then
        Assertions.assertTrue(result == null)
    }
}