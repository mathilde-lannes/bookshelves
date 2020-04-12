package com.maty.android.bookshelves.ui.books.all

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.BookViewModel
import com.maty.android.bookshelves.ui.books.add.AddBookActivity
import com.maty.android.bookshelves.ui.books.add.AddBookView
import com.maty.android.bookshelves.ui.books.detail.BookDetailsActivity
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AllBooksFragment : Fragment(), AddBookView {

  private lateinit var viewModel : BookViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_books, container, false)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initMenu()
    dismissFABOnScroll()
  }

  override fun onStop() {
    super.onStop()
    floatingActionMenu.close()
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

  private fun dismissFABOnScroll() {
    scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
      run {
        if (scrollY > oldScrollY) {
          floatingActionMenu.hide();
        } else {
          floatingActionMenu.show();
        }
      }
    }
  }

  private fun initMenu() {
    floatingActionMenu
        .setFirstAction(
                "", R.drawable.ic_barcode
        ) { openBarcodeScanner() }
        .setSecondAction(
                "", R.drawable.ic_search
        ) { startSearching() }
        .build()
  }

  private fun startSearching() {
    floatingActionMenu.hide()
    startActivity(Intent(requireContext(), AddBookActivity::class.java))
  }

  private fun openBarcodeScanner() {
    floatingActionMenu.hide()
    viewModel.scanBarcode(this)
  }

  override fun showBookError() {
    hideLoading()
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(requireContext(), resources.getString(R.string.book_error), duration)
    toast.show()
  }

  override fun onBookAdded(book: Book) {
    val intent = Intent(context, BookDetailsActivity::class.java)
    intent.putExtra("book", book)
    startActivity(intent)
  }

  override fun showLoading() {
    scrollView.visibility = View.GONE
    floatingActionMenu.hide()

    shimmerLayout.startShimmerAnimation()
    shimmerLayout.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    shimmerLayout.stopShimmerAnimation()
    shimmerLayout.visibility = View.GONE

    scrollView.visibility = View.VISIBLE
    floatingActionMenu.show()
  }

}