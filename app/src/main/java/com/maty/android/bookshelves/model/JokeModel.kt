package com.maty.android.bookshelves.model

data class JokeResponse(val id: String = "",
                        val authorName: String = "",
                        val authorId: String = "",
                        val text: String = "")

fun JokeResponse.isValid() = id.isNotBlank()
    && authorName.isNotBlank()
    && authorId.isNotBlank()
    && text.isNotBlank()

fun JokeResponse.mapToJoke() = Joke(id, authorName, authorId, text)

data class Joke(val id: String,
                val authorName: String,
                val authorId: String,
                val text: String,
                var isFavorite: Boolean = false)