package com.maty.android.bookshelves.ui.books.all


interface AllBooksView {

  fun showNoDataDescription()

  fun hideNoDataDescription()

  fun beforeFetchingBooks()

  fun afterFetchingBooks(hasResults: Boolean)
}