package com.maty.android.bookshelves.ui.books.detail

import com.maty.android.bookshelves.model.Book

interface BookDetailsView {

    fun redirectToHomepage()

    fun showBook(book: Book)

    fun showBookError()
}