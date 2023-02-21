package pl.mt.hello

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

@Singleton
class HelloRepositoryMongo(mongoClient: MongoClient) : HelloRepository {

    private val collection: MongoCollection<Hello>

    init {
        val db = mongoClient.getDatabase("pl/mt/hello")
        collection = db.getCollection("pl/mt/hello", Hello::class.java)
    }

    override fun findHelloByLanguage(language: String): String? {
        return collection.find(eq("language", language)).firstOrNull()?.hello
    }

    override fun putHelloInLanguage(language: String, hello: String) {
        collection.insertOne(Hello(language, hello))
    }
}

@Introspected
@Serdeable
data class Hello @Creator @BsonCreator constructor(
    @field:BsonProperty("language") @param:BsonProperty("language") val language: String,
    @field:BsonProperty("hello") @param:BsonProperty("hello") val hello: String
)