package com.maty.android.bookshelves.ui.books.all

import com.maty.android.bookshelves.model.Book

interface AllBooksView {

  fun showNoDataDescription()

  fun hideNoDataDescription()

  fun setGoodreadsTest(test: String)

  fun setFavoriteBooksIds(favoriteBooksIds: List<String>)

  fun addBook(book: Book)
}