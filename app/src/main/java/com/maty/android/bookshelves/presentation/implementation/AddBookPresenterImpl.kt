package com.maty.android.bookshelves.presentation.implementation

import android.app.Activity
import com.google.zxing.integration.android.IntentIntegrator
import com.intmainreturn00.grapi.Book
import com.intmainreturn00.grapi.SearchResult
import com.intmainreturn00.grapi.grapi
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
      onNewBook(book)
    }
  }

  override fun onNewBook(book: Book) {
    databaseInterface.addNewBook(book) { isSuccessful, bookId -> onAddBookResult(isSuccessful, bookId) }
  }

  override fun onSuggestionClicked(searchResult: SearchResult) {
    GlobalScope.launch {
      val book = grapi.getBookByGRID(searchResult.bookId)
      onNewBook(book)
    }
  }

  private fun onAddBookResult(isSuccessful: Boolean, bookId: String) {
    if (isSuccessful) {
      view.onBookAdded(bookId)
    } else {
      view.showAddBookError()
    }
  }

  override fun addToReadBook(book: Book) {
    databaseInterface.addBookToRead(book) { isSuccessful, bookId -> onAddBookResult(isSuccessful, bookId) }
  }

  override fun addToBuyBook(book: Book) {
    databaseInterface.addBookToBuy(book) { isSuccessful, bookId -> onAddBookResult(isSuccessful, bookId) }
  }

  override fun getBookById(bookId: String) {
    databaseInterface.getBookById(bookId) { view.showBook(it)}
  }
}