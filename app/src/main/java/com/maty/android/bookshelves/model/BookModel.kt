package com.maty.android.bookshelves.model

import com.intmainreturn00.grapi.Author
import com.intmainreturn00.grapi.Book

data class BookResponse(
        val id: String = "",
        val isbn: String = "",
        val isbn13: String = "",
        val textReviewsCount: Int? = 0,
        val title: String = "",
        val titleWithoutSeries: String = "",
        val imageUrl: String = "",
        val imageUrlSmall: String = "",
        val imageUrlLarge: String = "",
        val link: String = "",
        val numPages: Int? = 0,
        val format: String = "",
        val publisher: String = "",
        val editionInformation: String = "",
        val publicationDay: Int? = 0,
        val publicationYear: Int? = 0,
        val publicationMonth: Int? = 0,
        val averageRating: Float? = 0f,
        val ratingsCount: Int? = 0,
        val description: String = "",
        val authors: List<AuthorResponse> = listOf(),
        val workId: String = ""
)

data class AuthorResponse(
        val id: String = "",
        val name: String = "",
        val role: String = "",
        val imageUrl: String = "",
        val imageUrlSmall: String = "",
        val link: String = "",
        val averageRating: Float? = 0f,
        val ratingsCount: Int? = 0,
        val textReviewsCount: Int? = 0
)

fun AuthorResponse.mapToAuthor() = Author(id, name, role, imageUrl, imageUrlSmall, link, averageRating, ratingsCount, textReviewsCount)

fun BookResponse.mapToBook() = Book(id, isbn, isbn13, textReviewsCount, title, titleWithoutSeries, imageUrl, imageUrlSmall,
        imageUrlLarge, link, numPages, format, publisher, editionInformation, publicationDay, publicationYear, publicationMonth,
        averageRating, ratingsCount, description, authors.map(AuthorResponse::mapToAuthor), workId)