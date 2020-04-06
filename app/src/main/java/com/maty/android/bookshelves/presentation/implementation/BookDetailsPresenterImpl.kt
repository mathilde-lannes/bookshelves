package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.presentation.BookDetailsPresenter
import com.maty.android.bookshelves.ui.books.detail.BookDetailsView
import javax.inject.Inject

class BookDetailsPresenterImpl @Inject constructor(
        private val databaseInterface: FirebaseDatabaseInterface
) : BookDetailsPresenter {

    private lateinit var view: BookDetailsView

    override fun setView(view: BookDetailsView) {
        this.view = view
    }

    override fun addToReadBook(book: Book) {
        databaseInterface.removeBookToBuy(book)
        databaseInterface.addBookToRead(book) { onAddBookResult(it, book) }
    }

    override fun addToBuyBook(book: Book) {
        databaseInterface.addBookToBuy(book) { onAddBookResult(it, book) }
    }

    override fun addCurrentlyReadingBook(book: Book) {
        databaseInterface.removeBookToRead(book)
        databaseInterface.addBookCurrentlyReading(book) { onAddBookResult(it, book) }
    }

    private fun onAddBookResult(isSuccessful: Boolean, book: Book) {
        if (isSuccessful) {
            view.onBookAdded(book)
        } else {
            view.showBookError()
        }
    }
}