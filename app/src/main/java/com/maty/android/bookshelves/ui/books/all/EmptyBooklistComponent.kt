package com.maty.android.bookshelves.ui.books.all

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.maty.android.bookshelves.R
import kotlinx.android.synthetic.main.component_empty_booklist.view.*

class EmptyBooklistComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    private val booklistType = MutableLiveData<String>()

    private val activity: FragmentActivity by lazy {
        try {
            context as FragmentActivity
        } catch (exception: ClassCastException) {
            throw ClassCastException("Please ensure that the provided Context is a valid FragmentActivity")
        }
    }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.component_empty_booklist, this, true)

        booklistType.observe(activity, Observer { updateMessage(it) })

    }

    fun setType(type : String) { booklistType.value = type }

    private fun updateMessage(text : String) {
        when (text) {
            resources.getString(R.string.books_to_buy) -> displayMessage(R.string.no_books_to_buy_title, R.string.no_books_to_buy_subtitle)
            resources.getString(R.string.books_already_read) -> displayMessage(R.string.no_books_already_read_title, R.string.no_books_already_read_subtitle)
            resources.getString(R.string.currently_reading) -> displayMessage(R.string.no_books_currently_reading_title, R.string.no_books_currently_reading_subtitle)
            else -> displayMessage(R.string.no_books_to_read_title, R.string.no_books_to_read_subtitle)
        }
    }

    private fun displayMessage(title: Int, subtitle : Int) {
        messageTitle.text = resources.getString(title)
        messageSubtitle.text = resources.getString(subtitle)
    }
}
