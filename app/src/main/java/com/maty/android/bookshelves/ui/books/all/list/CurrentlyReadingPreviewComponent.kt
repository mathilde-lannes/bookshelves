package com.maty.android.bookshelves.ui.books.all.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.allBooksPresenter
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.all.AllBooksView
import kotlinx.android.synthetic.main.component_booklist_preview.view.noItems
import kotlinx.android.synthetic.main.component_booklist_preview.view.shimmerLayout
import kotlinx.android.synthetic.main.component_currently_reading_preview.view.*

class CurrentlyReadingPreviewComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes), AllBooksView {

    private val presenter by lazy { allBooksPresenter() }


    init {
        LayoutInflater.from(context)
                .inflate(R.layout.component_currently_reading_preview, this, true)

        orientation = VERTICAL

        presenter.setView(this)
        beforeFetchingBooks()
        presenter.displayBookCurrentlyReading()

    }

    override fun addBook(book: Book) {
        BookHolder(this).displayData(book)
    }

    override fun showNoDataDescription() {
        noItems.visibility = View.VISIBLE
    }

    override fun hideNoDataDescription() {
        noItems.visibility = View.GONE
    }

    override fun beforeFetchingBooks() {
        showPlaceholder()
    }

    override fun afterFetchingBooks(hasResults: Boolean) {
        hidePlaceholder()

        if (!hasResults) {
            itemCurrentlyReading.visibility = View.GONE
            showNoDataDescription()
        }
    }

    private fun showPlaceholder() {
        shimmerLayout.startShimmerAnimation()
        shimmerLayout.visibility = View.VISIBLE
    }

    private fun hidePlaceholder() {
        shimmerLayout.stopShimmerAnimation()
        shimmerLayout.visibility = View.GONE
    }
}