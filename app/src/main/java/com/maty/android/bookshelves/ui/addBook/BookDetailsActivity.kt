package com.maty.android.bookshelves.ui.addBook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intmainreturn00.grapi.Book
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.addBookPresenter
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_add_book_details.*

class BookDetailsActivity : AppCompatActivity(), AddBookView {

    private val presenter by lazy { addBookPresenter() }
    private lateinit var book : Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book_details)
        presenter.setView(this)

        val bookId = intent.getStringExtra("book")
        presenter.getBookById(bookId)

        initUi()
    }

    override fun showBook(book: Book) {
        this.book = book
        bookTitle.text = book.title
        bookAuthor.text = book.authors[0].name
        bookDescription.text = book.description
    }

    private fun initUi() {
        toReadButton.onClick { presenter.addToReadBook(this.book) }
        toBuyButton.onClick { presenter.addToBuyBook(this.book) }
    }

    override fun showAddBookError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showBookError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeBookError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBookAdded(bookId: String) {
        startActivity(MainActivity.getLaunchIntent(this))
    }
}