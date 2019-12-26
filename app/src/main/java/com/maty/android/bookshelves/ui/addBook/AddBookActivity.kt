package com.maty.android.bookshelves.ui.addBook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.intmainreturn00.grapi.Book
import com.intmainreturn00.grapi.SearchResult
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.addBookPresenter
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.common.showGeneralError
import com.maty.android.bookshelves.ui.addBook.suggestions.SuggestionAdapter
import kotlinx.android.synthetic.main.activity_add_book.*

class AddBookActivity : AppCompatActivity(), AddBookView {

  override fun showBook(book: Book) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private val presenter by lazy { addBookPresenter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_book)
    presenter.setView(this)

    initUi()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

    if(result != null){

      if(result.contents != null){
        presenter.getGoodreadsBookByISBN(result.contents)
      } else {
        showBookError()
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data)
    }
  }

  private fun initUi() {
    addBookButton.onClick { presenter.scanBarcode(this) }

    val adapter = SuggestionAdapter(this, R.layout.item_suggestion, listOf())
    autocomplete.setAdapter(adapter)

    autocomplete.setOnItemClickListener { parent, _, position, _ ->
      val selectedSuggestion = parent.adapter.getItem(position) as SearchResult?
      autocomplete.setText(selectedSuggestion?.bookTitle)
      if (selectedSuggestion != null)
        presenter.onSuggestionClicked(selectedSuggestion)
      else
        showBookError()
    }
  }

  override fun showBookError() {
    autocomplete.error = getString(R.string.book_error)
  }

  override fun removeBookError() {
    autocomplete.error = null
  }

  override fun onBookAdded(bookId: String) {
    val intent = Intent(this, BookDetailsActivity::class.java)
    intent.putExtra("book", bookId)
    startActivity(intent)
  }

  override fun showAddBookError() = showGeneralError(this)
}