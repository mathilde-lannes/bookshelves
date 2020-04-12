package com.maty.android.bookshelves.ui.books.add

import com.maty.android.bookshelves.model.Book

interface AddBookView {

  fun onBookAdded(book: Book)

  fun showBookError()

  fun showLoading()

  fun hideLoading()
}