package com.maty.android.bookshelves.ui.books.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.bookDetailsPresenter
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.common.showGeneralError
import com.maty.android.bookshelves.firebase.database.KEY_READING
import com.maty.android.bookshelves.firebase.database.KEY_TO_BUY
import com.maty.android.bookshelves.firebase.database.KEY_TO_READ
import com.maty.android.bookshelves.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_add_book_details.*

class BookDetailsActivity : AppCompatActivity(), BookDetailsView {

    private val presenter by lazy { bookDetailsPresenter() }
    private lateinit var book : Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book_details)
        presenter.setView(this)

        val book: Book = intent.getParcelableExtra("book")
        showBook(book)

        initUi()
    }

    override fun showBook(book: Book) {
        this.book = book
        bookTitle.text = book.title
        bookAuthor.text = book.authors[0].name
        bookDescription.text = book.description

        showBookActions()
    }

    private fun showBookActions() {
        when(book.status) {
            KEY_TO_BUY -> showToBuyAction()
            KEY_TO_READ -> showToReadAction()
            KEY_READING -> showFinishReadingAction()
            else -> showDefaultAction()
        }
    }

    private fun showToBuyAction() {
        toBuyAction.visibility = View.VISIBLE
    }

    private fun showToReadAction() {
        toReadAction.visibility = View.VISIBLE
    }

    private fun showFinishReadingAction() {
        finishReadingAction.visibility = View.VISIBLE
    }

    private fun showDefaultAction() {
        defaultAction.visibility = View.VISIBLE
    }

    private fun initUi() {
        toReadButton.onClick { presenter.addToReadBook(this.book) }
        moveToReadButton.onClick { presenter.addToReadBook(this.book) }
        toBuyButton.onClick { presenter.addToBuyBook(this.book) }
        startReadingButton.onClick { presenter.addCurrentlyReadingBook(this.book) }
        finishReadingButton.onClick { presenter.addAlreadyReadBook(this.book) }
    }

    override fun onBookAdded(book: Book) {
        val intent = MainActivity.getLaunchIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun showBookError() {
        showGeneralError(this)
    }
}