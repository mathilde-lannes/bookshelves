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

@Parcelize
data class Author(
        val id: String,
        val name: String,
        val imageUrl: String,
        val averageRating: Float?
) : Parcelable

fun AuthorResponse.mapToAuthor() = Author(id, name, imageUrl, averageRating)

fun com.intmainreturn00.grapi.Author.mapToAuthor() = Author(id, name, imageUrl, averageRating)