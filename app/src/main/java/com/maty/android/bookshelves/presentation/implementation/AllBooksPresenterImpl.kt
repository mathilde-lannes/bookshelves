package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.presentation.AllBooksPresenter
import com.maty.android.bookshelves.ui.books.all.AllBooksView
import javax.inject.Inject

class AllBooksPresenterImpl @Inject constructor(
    private val databaseInterface: FirebaseDatabaseInterface
) : AllBooksPresenter {
  private lateinit var view: AllBooksView

  override fun setView(view: AllBooksView) {
    this.view = view
  }

  override fun displayBooksToRead() {
    databaseInterface.listenToBooksToRead { view.addBook(it) }
  }

  override fun displayBooksToBuy() {
    databaseInterface.listenToBooksToBuy { view.addBook(it) }
  }

}

