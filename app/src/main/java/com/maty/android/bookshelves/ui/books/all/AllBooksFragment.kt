package com.maty.android.bookshelves.ui.books.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.ui.books.BookViewModel
import kotlinx.android.synthetic.main.activity_add_book_details.floatingActionMenu
import kotlinx.android.synthetic.main.fragment_books.*


class AllBooksFragment : Fragment() {

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
                resources.getString(R.string.scan_barcode), R.drawable.books
        ) { openBarcodeScanner() }
        .setSecondAction(
                resources.getString(R.string.search), R.drawable.shopping_cart
        ) { startSearching() }
        .build()
  }

  private fun startSearching() {
    floatingActionMenu.hide()
  }

  private fun openBarcodeScanner() {
    floatingActionMenu.hide()
  }
}