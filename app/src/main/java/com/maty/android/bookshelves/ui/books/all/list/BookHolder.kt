package com.maty.android.bookshelves.ui.books.all.list

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.intmainreturn00.grapi.Book
import kotlinx.android.synthetic.main.item_book.view.*

class BookHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

  fun displayData(book: Book) = with(itemView) {
    bookAuthor.text = book.authors[0].name
    bookDescription.text = book.description
  }
}