package com.maty.android.bookshelves.ui.books

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.maty.android.bookshelves.data.BookDb
import com.maty.android.bookshelves.firebase.database.KEY_ALREADY_READ
import com.maty.android.bookshelves.firebase.database.KEY_READING
import com.maty.android.bookshelves.firebase.database.KEY_TO_BUY
import com.maty.android.bookshelves.firebase.database.KEY_TO_READ
import com.maty.android.bookshelves.ioThread
import com.maty.android.bookshelves.model.Book

/**
 * A simple ViewModel that provides a paged list of books.
 */
class BookViewModel(app: Application) : AndroidViewModel(app) {
    val dao = BookDb.get(app).bookDao()

    val booksToRead = dao.booksByStatus(KEY_TO_READ)
    val booksToBuy = dao.booksByStatus(KEY_TO_BUY)
    val booksAlreadyRead = dao.booksByStatus(KEY_ALREADY_READ)
    val booksCurrentlyReading = dao.booksByStatus(KEY_READING)

    fun insert(book: Book) = ioThread {
        dao.insert(book)
    }

    fun update(book: Book) = ioThread {
        dao.update(book)
    }

    fun remove(book: Book) = ioThread {
        dao.delete(book)
    }
}