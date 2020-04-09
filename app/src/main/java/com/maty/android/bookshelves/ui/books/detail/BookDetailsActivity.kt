package com.maty.android.bookshelves.ui.books.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.common.showGeneralError
import com.maty.android.bookshelves.ui.books.BookViewModel
import com.maty.android.bookshelves.ui.books.*
import com.maty.android.bookshelves.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_add_book_details.*

class BookDetailsActivity : AppCompatActivity(), BookDetailsView {

    private val viewModel by viewModels<BookViewModel>()
    private lateinit var book : Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book_details)

        val book: Book = intent.getParcelableExtra("book")
        showBook(book)

        initUi()
    }

    override fun showBook(book: Book) {
        this.book = book
        bookTitle.text = book.title
        bookAuthor.text = book.author?.name
        bookDescription.text = book.description

        showBookActions()
    }

    private fun showBookActions() {
        when(book.status) {
            KEY_TO_BUY -> toBuyAction.visibility = View.VISIBLE
            KEY_TO_READ -> toReadAction.visibility = View.VISIBLE
            KEY_READING -> finishReadingAction.visibility = View.VISIBLE
            else -> defaultAction.visibility = View.VISIBLE
        }
    }

    private fun registerBook(status : String) {
        viewModel.insert(this.book, status)
        redirectToHomepage()
    }

    private fun updateBook(status : String) {
        this.book.status = status
        viewModel.update(this.book)
        redirectToHomepage()
    }

    private fun initUi() {
        toReadButton.onClick { registerBook(KEY_TO_READ) }
        moveToReadButton.onClick { updateBook(KEY_TO_READ) }
        toBuyButton.onClick { registerBook(KEY_TO_BUY) }
        startReadingButton.onClick { updateBook(KEY_READING) }
        finishReadingButton.onClick { updateBook(KEY_ALREADY_READ) }
    }

    override fun redirectToHomepage() {
        val intent = MainActivity.getLaunchIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun showBookError() {
        showGeneralError(this)
    }
}