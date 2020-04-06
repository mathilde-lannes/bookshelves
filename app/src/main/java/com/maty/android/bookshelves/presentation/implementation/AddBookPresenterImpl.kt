package com.maty.android.bookshelves.presentation.implementation

import android.app.Activity
import com.google.zxing.integration.android.IntentIntegrator
import com.maty.android.bookshelves.model.Book
import com.intmainreturn00.grapi.SearchResult
import com.intmainreturn00.grapi.grapi
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.model.mapToBook
import com.maty.android.bookshelves.presentation.AddBookPresenter
import com.maty.android.bookshelves.ui.addBook.AddBookView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddBookPresenterImpl @Inject constructor(
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
      val book = grapi.getBookByISBN(isbn).mapToBook()
      onNewBook(book)
    }
  }

  override fun onNewBook(book: Book) {
    view.onBookAdded(book)
  }

  override fun onSuggestionClicked(searchResult: SearchResult) {
    GlobalScope.launch {
      val book = grapi.getBookByGRID(searchResult.bookId).mapToBook()
      onNewBook(book)
    }
  }
}