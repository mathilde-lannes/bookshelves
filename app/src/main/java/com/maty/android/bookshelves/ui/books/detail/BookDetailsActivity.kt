package com.maty.android.bookshelves.ui.books.detail

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.BookAlreadySaved
import com.maty.android.bookshelves.common.getDominantColor
import com.maty.android.bookshelves.common.showGeneralError
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.*
import com.maty.android.bookshelves.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_add_book_details.*
import kotlinx.android.synthetic.main.item_currently_reading_preview.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class BookDetailsActivity : AppCompatActivity(), BookDetailsView {

    private val viewModel by viewModels<BookViewModel>()
    private lateinit var book : Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book_details)

        val book: Book = intent.getParcelableExtra("book")
        showBook(book)
        dismissFABOnScroll()

    }

    override fun showBook(book: Book) {
        this.book = book
        bookPreview.title.text = book.title
        bookPreview.author.text = book.author?.name
        bookDescription.text = Html.fromHtml(book.description, Html.FROM_HTML_MODE_LEGACY)

        if (book.imageUrl.contains("nophoto")) {
            fetchAndDisplayThumbnail(book)
        } else {
            Glide.with(applicationContext).load(book.imageUrl).into(bookPreview.cover)

            GlobalScope.launch {
                val bitmap: Bitmap = Glide.with(applicationContext).asBitmap().load(book.imageUrl)
                        .submit().get()

                coloredBackground.setBackgroundColor(getDominantColor(bitmap))
            }
        }

        showBookActions()
    }

    private fun fetchAndDisplayThumbnail(book: Book) {
        GlobalScope.launch {
            val img = viewModel.getThumbnail(book.title).replace("http", "https")
            val bitmap: Bitmap = Glide.with(applicationContext).asBitmap().load(img).submit().get()

            coloredBackground.setBackgroundColor(getDominantColor(bitmap))
            runOnUiThread {
                Glide.with(applicationContext).load(img).into(bookPreview.cover)
                updateBookImage(img)
            }
        }
    }

    private fun showBookActions() {
        when(book.status) {
            KEY_TO_BUY -> showToBuyActions()
            KEY_TO_READ -> showToReadActions()
            KEY_READING -> showFinishReadingActions()
            else -> showDefaultActions()
        }
    }

    private fun showDefaultActions() {
        floatingActionMenu
            .setFirstAction(
                    resources.getString(R.string.to_read), R.drawable.books
            ) { registerBook(KEY_TO_READ) }
            .setSecondAction(
                resources.getString(R.string.to_buy), R.drawable.shopping_cart
            ) { registerBook(KEY_TO_BUY) }
            .build()
    }

    private fun showToBuyActions() {
        floatingActionMenu
            .setFirstAction(
                resources.getString(R.string.move_to_read), R.drawable.books
            ) { updateBook(KEY_TO_READ) }
            .build()
    }

    private fun showToReadActions() {
        floatingActionMenu
            .setFirstAction(
                resources.getString(R.string.start_reading), R.drawable.books
            ) { updateBook(KEY_READING) }
            .build()
    }

    private fun showFinishReadingActions() {
        floatingActionMenu
            .setFirstAction(
                resources.getString(R.string.finish_reading),
                R.drawable.books
            ) { updateBook(KEY_ALREADY_READ) }
            .build()
    }

    private fun registerBook(status : String) {
        try {
            viewModel.insert(this.book, status)
            redirectToHomepage()
        } catch (e : BookAlreadySaved) {
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(this, e.message, duration)
            floatingActionMenu.close()
            toast.show()
        }
    }

    private fun updateBook(status : String) {
        this.book.status = status
        viewModel.update(this.book)
        redirectToHomepage()
    }

    private fun updateBookImage(img : String) {
        this.book.imageUrl = img
    }

    override fun redirectToHomepage() {
        val intent = MainActivity.getLaunchIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun showBookError() {
        showGeneralError(this)
    }

    private fun dismissFABOnScroll() {
        scrollable.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            run {
                if (scrollY > oldScrollY) {
                    floatingActionMenu.hide();
                } else {
                    floatingActionMenu.show();
                }
            }
        }
    }
}