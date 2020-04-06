package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.detail.BookDetailsView

interface BookDetailsPresenter : BasePresenter<BookDetailsView> {

    fun addToReadBook(book: Book)

    fun addToBuyBook(book: Book)

    fun addCurrentlyReadingBook(book: Book)

    fun addAlreadyReadBook(book: Book)
}