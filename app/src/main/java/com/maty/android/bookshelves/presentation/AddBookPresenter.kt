package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.ui.addBook.AddBookView

interface AddBookPresenter : BasePresenter<AddBookView> {

  fun addBookTapped()

  fun onBookTextChanged(bookText: String)
}