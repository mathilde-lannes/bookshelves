package com.maty.android.bookshelves.ui.books.all

import com.maty.android.bookshelves.model.Book


interface AllBooksView {

  fun showNoDataDescription()

  fun hideNoDataDescription()

  fun setFavoriteBooksIds(favoriteBooksIds: List<String>)

  fun addBook(book: Book)
}