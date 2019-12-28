package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.ui.books.all.AllBooksView

interface AllBooksPresenter : BasePresenter<AllBooksView> {

  fun displayBooksToRead()

  fun displayBooksToBuy()

}