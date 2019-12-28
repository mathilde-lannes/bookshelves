package com.maty.android.bookshelves.presentation

import android.app.Activity
import com.maty.android.bookshelves.model.Book
import com.intmainreturn00.grapi.SearchResult
import com.maty.android.bookshelves.ui.addBook.AddBookView

interface AddBookPresenter : BasePresenter<AddBookView> {

  fun scanBarcode(activity: Activity)

  fun getGoodreadsBookByISBN(isbn: String)

  fun onNewBook(book: Book)

  fun onSuggestionClicked(searchResult: SearchResult)
}