package com.maty.android.bookshelves.ui.books

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.zxing.integration.android.IntentIntegrator
import com.intmainreturn00.grapi.grapi
import com.maty.android.bookshelves.data.BookDb
import com.maty.android.bookshelves.ioThread
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.model.mapToBook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.time.LocalDateTime

const val NB_PREVIEW_COLLECTION = 5
const val NB_PREVIEW_READING = 1

const val KEY_TO_READ = "read"
const val KEY_ALREADY_READ = "already_read"
const val KEY_READING = "reading"
const val KEY_TO_BUY = "buy"
/**
 * A simple ViewModel that provides a paged list of books.
 */
class BookViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = BookDb.get(app).bookDao()

    val booksToReadPreview = dao.booksByStatus(KEY_TO_READ, NB_PREVIEW_COLLECTION)
    val booksToBuyPreview = dao.booksByStatus(KEY_TO_BUY, NB_PREVIEW_COLLECTION)
    val booksAlreadyReadPreview = dao.booksByStatus(KEY_ALREADY_READ, NB_PREVIEW_COLLECTION)
    val booksCurrentlyReadingPreview = dao.booksByStatus(KEY_READING, NB_PREVIEW_READING)

    fun insert(book: Book) = ioThread {
        dao.insert(book)
    }

    fun insert(book: Book, status : String) = ioThread {
        book.status = status
        book.entryDate = LocalDateTime.now()
        dao.insert(book)
    }

    fun update(book: Book) = ioThread {
        book.entryDate = LocalDateTime.now()
        dao.update(book)
    }

    fun remove(book: Book) = ioThread {
        dao.delete(book)
    }

    suspend fun findBookByISBN(isbn: String) : Book = withContext(Dispatchers.IO) {
        grapi.getBookByISBN(isbn).mapToBook()
    }

    suspend fun findBookByGRID(id: String) : Book = withContext(Dispatchers.IO) {
        grapi.getBookByGRID(id).mapToBook()
    }

    fun scanBarcode(activity : Activity) {
        run {
            IntentIntegrator(activity).initiateScan()
        }
    }

    suspend fun getThumbnail(query : String) : String {
        return withContext(Dispatchers.IO) {
           getImageFromGoogle(query)
        }
    }

    private fun getImageFromGoogle(query : String) : String {
        val doc = Jsoup.connect("https://www.google.com/search?tbm=isch&q=$query")
                .userAgent("Mozilla")
                .get()
        val img = doc.select("img")[4]
        return img.attr("src")
    }
}