package com.maty.android.bookshelves.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

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

@Entity
@Parcelize
data class Author(
        @PrimaryKey val id: String,
        val name: String,
        val role: String,
        val imageUrl: String,
        val imageUrlSmall: String,
        val link: String,
        val averageRating: Float?,
        val ratingsCount: Int?,
        val textReviewsCount: Int?
) : Parcelable

fun AuthorResponse.mapToAuthor() = Author(id, name, role, imageUrl, imageUrlSmall, link, averageRating, ratingsCount, textReviewsCount)

fun com.intmainreturn00.grapi.Author.mapToAuthor() = Author(id, name, role, imageUrl, imageUrlSmall, link, averageRating, ratingsCount, textReviewsCount)