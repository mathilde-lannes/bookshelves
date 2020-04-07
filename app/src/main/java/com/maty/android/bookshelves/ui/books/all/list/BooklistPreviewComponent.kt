package com.maty.android.bookshelves.ui.books.all.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.allBooksPresenter
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.all.AllBooksView
import kotlinx.android.synthetic.main.component_booklist_preview.view.*

class BooklistPreviewComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes), AllBooksView {

    private val presenter by lazy { allBooksPresenter() }
    private val adapter by lazy { BookAdapter() }


    init {
        LayoutInflater.from(context)
                .inflate(R.layout.component_booklist_preview, this, true)

        orientation = VERTICAL

        initAttributes(attrs)
        initUi()

        presenter.setView(this)

    }

    private fun initUi() {
        books.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        books.adapter = adapter
        showPlaceholder()
//        seeAll.onClick { context.startActivity(Intent(context, AddBookActivity::class.java))  }
    }

    override fun showPlaceholder() {
        shimmerLayout.startShimmerAnimation()
        shimmerLayout.visibility = View.VISIBLE
    }

    override fun hidePlaceholder() {
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

            mainTitle.text = title

            when(title) {
                resources.getString(R.string.books_to_buy) -> presenter.displayBooksToBuy()
                resources.getString(R.string.books_already_read) -> presenter.displayBooksAlreadyRead()
                else -> presenter.displayBooksToRead()
            }

            typedArray.recycle()
        }
    }

    override fun addBook(book: Book) {
        adapter.addBook(book)
        noItems.visibility = if (adapter.itemCount!=0) View.INVISIBLE else View.VISIBLE
    }

    override fun showNoDataDescription() {
        noItems.visibility = View.VISIBLE
    }

    override fun hideNoDataDescription() {
        noItems.visibility = View.GONE
    }
}