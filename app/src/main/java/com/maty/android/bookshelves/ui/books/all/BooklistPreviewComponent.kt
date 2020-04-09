package com.maty.android.bookshelves.ui.books.all

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.BookViewModel
import kotlinx.android.synthetic.main.component_booklist_preview.view.*

class BooklistPreviewComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes), AllBooksView {

    private var viewModel : BookViewModel
    private val adapter by lazy { BookAdapter() }

    private val activity: FragmentActivity by lazy {
        try {
            context as FragmentActivity
        } catch (exception: ClassCastException) {
            throw ClassCastException("Please ensure that the provided Context is a valid FragmentActivity")
        }
    }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.component_booklist_preview, this, true)

        orientation = VERTICAL
        viewModel = ViewModelProvider(activity).get(BookViewModel::class.java)

        initUi()
        initAttributes(attrs)

    }

    private fun initUi() {
        books.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        books.adapter = adapter
        beforeFetchingBooks()
    }

    override fun beforeFetchingBooks() {
        showPlaceholders()
        hideNoDataDescription()
    }

    override fun afterFetchingBooks(hasResults: Boolean) {
        hidePlaceholders()

        if (!hasResults) {
            showNoDataDescription()
        }
    }

    private fun showPlaceholders() {
        shimmerLayout.startShimmerAnimation()
        shimmerLayout.visibility = View.VISIBLE
    }

    private fun hidePlaceholders() {
        shimmerLayout.stopShimmerAnimation()
        shimmerLayout.visibility = View.GONE
    }

    private fun initAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                    R.styleable.booklist_preview_component_attributes, 0, 0)
            val title = resources.getText(typedArray
                    .getResourceId(R.styleable
                            .booklist_preview_component_attributes_booklist_title,
                            R.string.books_to_read))

            noItems.setType(title.toString())
            mainTitle.text = title

            val bookObserver = Observer<List<Book>> { books ->
                afterFetchingBooks(books.isNotEmpty())
                adapter.addBooks(books)
            }

            when(title) {
                resources.getString(R.string.books_to_buy) -> viewModel.booksToBuyPreview.observe(activity, bookObserver)
                resources.getString(R.string.books_already_read) -> viewModel.booksAlreadyReadPreview.observe(activity, bookObserver)
                else -> viewModel.booksToReadPreview.observe(activity, bookObserver)
            }

            typedArray.recycle()
        }
    }

    override fun showNoDataDescription() {
        noItems.visibility = View.VISIBLE
    }

    override fun hideNoDataDescription() {
        noItems.visibility = View.GONE
    }
}
