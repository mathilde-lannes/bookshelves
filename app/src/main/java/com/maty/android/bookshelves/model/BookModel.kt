package com.maty.android.bookshelves.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

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
        val workId: String = "",
        val status: String = ""
)

@Parcelize
@Entity
data class Book(
        @PrimaryKey val id: String,
        val isbn: String,
        val isbn13: String,
        val textReviewsCount: Int?,
        val title: String,
        val titleWithoutSeries: String,
        var imageUrl: String,
        val imageUrlSmall: String,
        val imageUrlLarge: String,
        val link: String,
        val numPages: Int?,
        val format: String,
        val publisher: String,
        val editionInformation: String,
        val publicationDay: Int?,
        val publicationYear: Int?,
        val publicationMonth: Int?,
        val averageRating: Float?,
        val ratingsCount: Int?,
        val description: String,
        @Embedded(prefix = "author_") val author: Author?,
        val workId: String,
        var status: String,
        var entryDate: LocalDateTime
) : Parcelable

fun BookResponse.mapToBook() = Book(id, isbn, isbn13, textReviewsCount, title, titleWithoutSeries, imageUrl, imageUrlSmall,
        imageUrlLarge, link, numPages, format, publisher, editionInformation, publicationDay, publicationYear, publicationMonth,
        averageRating, ratingsCount, description, authors[0].mapToAuthor(), workId, status, LocalDateTime.now())

fun com.intmainreturn00.grapi.Book.mapToBook() = Book(id, isbn, isbn13, textReviewsCount, title, titleWithoutSeries, imageUrl, imageUrlSmall,
        imageUrlLarge, link, numPages, format, publisher, editionInformation, publicationDay, publicationYear, publicationMonth,
        averageRating, ratingsCount, description, authors[0].mapToAuthor(), workId, "added", LocalDateTime.now())
