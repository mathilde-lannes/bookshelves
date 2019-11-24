package com.maty.android.bookshelves.model

data class BookResponse(val id: String = "",
                        val authorName: String = "",
                        val authorId: String = "",
                        val text: String = "")

fun BookResponse.isValid() = id.isNotBlank()
    && authorName.isNotBlank()
    && authorId.isNotBlank()
    && text.isNotBlank()

fun BookResponse.mapToBook() = Book(id, authorName, authorId, text)

data class Book(val id: String,
                val authorName: String,
                val authorId: String,
                val text: String,
                var isFavorite: Boolean = false)