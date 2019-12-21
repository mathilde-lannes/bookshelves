package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.all.AllBooksView

interface AllBooksPresenter : BasePresenter<AllBooksView> {

  fun viewReady()

  fun getAllBooks()

  fun getGoodreadsBookByISBN(isbn: String)

  fun onFavoriteButtonTapped(book: Book)
}