package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.presentation.AllBooksPresenter
import com.maty.android.bookshelves.ui.books.all.AllBooksView
import javax.inject.Inject

class AllBooksPresenterImpl @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : AllBooksPresenter {
  private lateinit var view: AllBooksView

  override fun setView(view: AllBooksView) {
    this.view = view
  }

  override fun viewReady() {
    databaseInterface.getFavoriteBooks(authenticationInterface.getUserId()) { onFavoriteBooksResult(it) }
    getAllBooks()
  }

  private fun onFavoriteBooksResult(favoriteBooks: List<Book>) = view.setFavoriteBooksIds(favoriteBooks.map { it.id })

  override fun getAllBooks() {
    databaseInterface.listenToBooksToRead { view.addBook(it) }
//    databaseInterface.listenToBooksToBuy { view.addBook(it) }
  }
}

