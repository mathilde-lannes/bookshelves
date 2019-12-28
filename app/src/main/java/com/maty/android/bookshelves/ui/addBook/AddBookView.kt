package com.maty.android.bookshelves.ui.addBook

import com.maty.android.bookshelves.model.Book

interface AddBookView {

  fun onBookAdded(book: Book)

  fun showAddBookError()

  fun showBookError()

  fun removeBookError()
}