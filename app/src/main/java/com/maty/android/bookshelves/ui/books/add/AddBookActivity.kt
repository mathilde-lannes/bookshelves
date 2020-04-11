package com.maty.android.bookshelves.ui.books.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.intmainreturn00.grapi.SearchResult
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.common.showGeneralError
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.BookViewModel
import com.maty.android.bookshelves.ui.books.add.suggestions.SuggestionAdapter
import com.maty.android.bookshelves.ui.books.detail.BookDetailsActivity
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddBookActivity : AppCompatActivity(), AddBookView {

  private val viewModel by viewModels<BookViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_book)

    initUi()
  }

  override fun onStop() {
    super.onStop()
    hideLoading()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

    if (result != null) {
      showLoading()
      if (result.contents != null) {
        GlobalScope.launch {
          val book = viewModel.findBookByISBN(result.contents)
          onBookAdded(book)
        }
      } else
        showBookError()
    } else
      super.onActivityResult(requestCode, resultCode, data)
  }

  private fun initUi() {
    addBookButton.onClick { viewModel.scanBarcode(this) }

    val adapter = SuggestionAdapter(this, R.layout.item_suggestion, listOf())
    adapter.setViews(autocomplete, progressBar)
    autocomplete.setAdapter(adapter)

    autocomplete.doAfterTextChanged {
      if (autocomplete.text.toString().length > 3)
        progressBar.visibility = View.VISIBLE
    }

    autocomplete.setOnItemClickListener { parent, _, position, _ ->
      val selectedSuggestion = parent.adapter.getItem(position) as SearchResult?
      autocomplete.setText(selectedSuggestion?.bookTitle)

      if (selectedSuggestion != null) {
        GlobalScope.launch {
          showLoading()
          val book = viewModel.findBookByGRID(selectedSuggestion.bookId)
          onBookAdded(book)
        }
      }
      else
        showBookError()
    }
  }

  override fun showBookError() {
    hideLoading()
    autocomplete.error = getString(R.string.book_error)
  }

  override fun removeBookError() {
    autocomplete.error = null
  }

  override fun onBookAdded(book: Book) {
    val intent = Intent(this, BookDetailsActivity::class.java)
    intent.putExtra("book", book)
    startActivity(intent)
  }

  override fun showAddBookError() = showGeneralError(this)

  override fun showLoading() {
    runOnUiThread {
      autocomplete.hideKeyboard()
      autocomplete.visibility = View.GONE
      or.visibility = View.GONE
      addBookButton.visibility = View.GONE

      shimmerLayout.startShimmerAnimation()
      shimmerLayout.visibility = View.VISIBLE
    }
  }

  override fun hideLoading() {
    runOnUiThread {
      shimmerLayout.stopShimmerAnimation()
      shimmerLayout.visibility = View.GONE

      autocomplete.visibility = View.VISIBLE
      or.visibility = View.VISIBLE
      addBookButton.visibility = View.VISIBLE
    }
  }

  private fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
  }
}