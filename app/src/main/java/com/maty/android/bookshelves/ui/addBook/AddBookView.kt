package com.maty.android.bookshelves.ui.addBook

interface AddBookView {

  fun onBookAdded()

  fun showAddBookError()

  fun showBookError()

  fun removeBookError()
}