package com.maty.android.bookshelves.ui.books.all

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.allBooksPresenter
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.all.list.BookAdapter
import kotlinx.android.synthetic.main.fragment_books.*

class AllBooksFragment : Fragment(), AllBooksView {

  private val presenter by lazy { allBooksPresenter() }
  private val adapter by lazy { BookAdapter(presenter::onFavoriteButtonTapped) }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_books, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initUi()
    presenter.setView(this)

    presenter.viewReady()
  }

  override fun addBook(book: Book) {
    adapter.addBook(book)
    noItems.visibility = if (adapter.itemCount!=0) View.INVISIBLE else View.VISIBLE
  }

  private fun initUi() {
    books.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    books.adapter = adapter
  }

  override fun showNoDataDescription() {
    noItems.visibility = View.VISIBLE
  }

  override fun hideNoDataDescription() {
    noItems.visibility = View.GONE
  }

  override fun setFavoriteBooksIds(favoriteBooksIds: List<String>) = adapter.setFavoriteBooksIds(favoriteBooksIds)
}