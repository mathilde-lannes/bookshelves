package com.maty.android.bookshelves.ui.addBook

import com.intmainreturn00.grapi.Book

interface AddBookView {

  fun onBookAdded(bookId: String)

  fun showAddBookError()

  fun showBookError()

  fun removeBookError()

  fun showBook(book: Book)
}