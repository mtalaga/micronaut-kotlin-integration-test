package pl.mt.word

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import io.micronaut.context.annotation.Property
import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

@Singleton
class WordRepositoryMongo(
    mongoClient: MongoClient,
    @Property(name = "word.database") databaseName: String,
    @Property(name = "word.collection") collectionName: String
) : WordRepository {

    private val collection: MongoCollection<MongoWord>

    init {
        val db = mongoClient.getDatabase(databaseName)
        collection = db.getCollection(collectionName, MongoWord::class.java)
    }

    override fun findWord(word: String): Word? {
        return collection.find(eq("word", word)).firstOrNull()?.toWord()
    }

    override fun putWord(word: Word) {
        collection.insertOne(MongoWord.fromWord(word))
    }
}

@Introspected
@Serdeable
data class MongoWord @Creator @BsonCreator constructor(
    @field:BsonProperty("word") @param:BsonProperty("word") val word: String,
    @field:BsonProperty("translations") @param:BsonProperty("translations") val translations: List<MongoTranslation>
) {
    fun toWord(): Word {
        return Word(word, translations.map { it.toTranslation() })
    }

    companion object {
        fun fromWord(word: Word): MongoWord {
            return MongoWord(word.word, word.translations.map { MongoTranslation.fromTranslation(it) })
        }
    }
}

@Introspected
@Serdeable
data class MongoTranslation @Creator @BsonCreator constructor(
    @field:BsonProperty("language") @param:BsonProperty("language") val language: String,
    @field:BsonProperty("translation") @param:BsonProperty("translation") val translation: String
) {

    fun toTranslation(): Translation {
        return Translation(language, translation)
    }

    companion object {
        fun fromTranslation(translation: Translation): MongoTranslation {
            return MongoTranslation(translation.language, translation.translation)
        }
    }
}