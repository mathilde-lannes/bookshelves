package com.maty.android.bookshelves.presentation.implementation

import com.intmainreturn00.grapi.grapi
import com.maty.android.bookshelves.common.ScopedAppActivity
import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.presentation.AllBooksPresenter
import com.maty.android.bookshelves.ui.books.all.AllBooksView
import kotlinx.coroutines.*
import org.jetbrains.anko.browse
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

    if (grapi.isLoggedIn() && authenticationInterface.getUserId().isNotEmpty()) {
      getGoodreadsBookByISBN("2841728641")
    }
  }

  private fun onFavoriteBooksResult(favoriteBooks: List<Book>) = view.setFavoriteBooksIds(favoriteBooks.map { it.id })

  override fun getAllBooks() = databaseInterface.listenToBooks { view.addBook(it) }

  override fun getGoodreadsBookByISBN(isbn: String) {
    GlobalScope.launch {
      val book = grapi.getBookByISBN(isbn)
      view.setGoodreadsTest(book.title)
    }
  }

  override fun onFavoriteButtonTapped(book: Book) {
    databaseInterface.changeBookFavoriteStatus(book, authenticationInterface.getUserId())
  }
}

