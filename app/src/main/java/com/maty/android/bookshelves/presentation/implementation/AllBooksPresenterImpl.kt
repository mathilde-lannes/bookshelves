package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.presentation.AllBooksPresenter
import com.maty.android.bookshelves.ui.books.all.AllBooksView
import javax.inject.Inject

const val MAX_BOOKS_PREVIEW = 5

class AllBooksPresenterImpl @Inject constructor(
    private val databaseInterface: FirebaseDatabaseInterface
) : AllBooksPresenter {
  private lateinit var view: AllBooksView

  override fun setView(view: AllBooksView) {
    this.view = view
  }

  override fun displayBooksToRead() {
    databaseInterface.listenToBooksToRead(MAX_BOOKS_PREVIEW) { view.addBook(it) }
  }

  override fun displayBooksToBuy() {
    databaseInterface.listenToBooksToBuy(MAX_BOOKS_PREVIEW) { view.addBook(it) }
  }

  override fun displayBooksAlreadyRead() {
    databaseInterface.listenToBooksAlreadyRead(MAX_BOOKS_PREVIEW) { view.addBook(it) }
  }

  override fun displayBookCurrentlyReading() {
    databaseInterface.listenToBooksCurrentlyReading(1) { view.addBook(it) }
  }

}

