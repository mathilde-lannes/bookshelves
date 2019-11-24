package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.common.isValidBook
import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.presentation.AddBookPresenter
import com.maty.android.bookshelves.ui.addBook.AddBookView
import javax.inject.Inject

class AddBookPresenterImpl @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : AddBookPresenter {

  private lateinit var view: AddBookView

  private var bookText = ""

  override fun setView(view: AddBookView) {
    this.view = view
  }

  override fun addBookTapped() {
    if (isValidBook(bookText)) {
      val book = Book("", authenticationInterface.getUserName(), authenticationInterface.getUserId(), bookText)

      databaseInterface.addNewBook(book) { onAddBookResult(it) }
    }
  }

  override fun onBookTextChanged(bookText: String) {
    this.bookText = bookText

    if (!isValidBook(bookText)) {
      view.showBookError()
    } else {
      view.removeBookError()
    }
  }

  private fun onAddBookResult(isSuccessful: Boolean) {
    if (isSuccessful) {
      view.onBookAdded()
    } else {
      view.showAddBookError()
    }
  }
}