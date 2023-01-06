package pl.mt.word

interface WordRepository {

    fun findWord(word: String): Word?
    fun putWord(word: Word)
}

data class Word(val word: String, val translations: List<Translation>)
data class Translation(val language: String, val translation: String)