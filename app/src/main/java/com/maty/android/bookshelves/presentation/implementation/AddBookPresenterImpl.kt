package com.maty.android.bookshelves.presentation.implementation

import android.app.Activity
import com.google.zxing.integration.android.IntentIntegrator
import com.intmainreturn00.grapi.grapi
import com.maty.android.bookshelves.common.isValidBook
import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.presentation.AddBookPresenter
import com.maty.android.bookshelves.ui.addBook.AddBookView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

  override fun scanBarcode(activity: Activity) {
    run {
      IntentIntegrator(activity).initiateScan()
    }
  }

  override fun getGoodreadsBookByISBN(isbn: String) {
    GlobalScope.launch {
      val book = grapi.getBookByISBN(isbn)

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