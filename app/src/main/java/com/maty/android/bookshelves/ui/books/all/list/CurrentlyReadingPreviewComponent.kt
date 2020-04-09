package com.maty.android.bookshelves.ui.books.all.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.BookViewModel
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

    private var viewModel : BookViewModel

    private val activity: FragmentActivity by lazy {
        try {
            context as FragmentActivity
        } catch (exception: ClassCastException) {
            throw ClassCastException("Please ensure that the provided Context is a valid FragmentActivity")
        }
    }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.component_currently_reading_preview, this, true)

        orientation = VERTICAL

        val bookObserver = Observer<List<Book>> { books ->
            if (books.isEmpty()) {
                itemCurrentlyReading.visibility = View.GONE
                showNoDataDescription()
            } else {
                BookHolder(this).displayData(books[0])
            }
        }

        viewModel = ViewModelProvider(activity).get(BookViewModel::class.java)
        viewModel.booksCurrentlyReadingPreview.observe(activity, bookObserver)

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