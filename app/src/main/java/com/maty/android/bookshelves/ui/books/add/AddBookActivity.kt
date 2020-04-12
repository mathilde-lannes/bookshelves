package com.maty.android.bookshelves.ui.books.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.intmainreturn00.grapi.SearchResult
import com.maty.android.bookshelves.R
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

  private fun initUi() {

    val adapter = SuggestionAdapter(this, R.layout.item_suggestion, listOf())
    adapter.setViews(autocomplete, progressBar)
    autocomplete.setAdapter(adapter)
    autocomplete.requestFocus()
    autocomplete.showKeyboard()

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

  override fun onBookAdded(book: Book) {
    val intent = Intent(this, BookDetailsActivity::class.java)
    intent.putExtra("book", book)
    startActivity(intent)
  }

  override fun showLoading() {
    runOnUiThread {
      autocomplete.hideKeyboard()
      autocomplete.visibility = View.GONE

      shimmerLayout.startShimmerAnimation()
      shimmerLayout.visibility = View.VISIBLE
    }
  }

  override fun hideLoading() {
    runOnUiThread {
      shimmerLayout.stopShimmerAnimation()
      shimmerLayout.visibility = View.GONE

      autocomplete.visibility = View.VISIBLE
    }
  }

  private fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
  }

  private fun View.showKeyboard() {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
  }
}