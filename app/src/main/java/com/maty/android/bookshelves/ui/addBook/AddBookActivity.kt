package com.maty.android.bookshelves.ui.addBook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.addBookPresenter
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.common.onTextChanged
import com.maty.android.bookshelves.common.showGeneralError
import kotlinx.android.synthetic.main.activity_add_book.*

class AddBookActivity : AppCompatActivity(), AddBookView {

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
    bookDescription.onTextChanged { presenter.onBookTextChanged(it) }
    addBook.onClick { presenter.scanBarcode(this) }
  }

  override fun showBookError() {
    bookDescription.error = getString(R.string.book_error)
  }

  override fun removeBookError() {
    bookDescription.error = null
  }

  override fun onBookAdded() = finish()

  override fun showAddBookError() = showGeneralError(this)
}