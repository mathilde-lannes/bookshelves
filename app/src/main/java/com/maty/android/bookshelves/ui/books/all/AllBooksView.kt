package com.maty.android.bookshelves.ui.books.all

import com.maty.android.bookshelves.model.Book


interface AllBooksView {

  fun showNoDataDescription()

  fun hideNoDataDescription()

  fun addBook(book: Book)

  fun beforeFetchingBooks()

  fun afterFetchingBooks()
}