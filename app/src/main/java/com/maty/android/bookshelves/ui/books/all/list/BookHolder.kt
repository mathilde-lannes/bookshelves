package com.maty.android.bookshelves.ui.books.all.list

import android.support.v7.widget.RecyclerView
import android.view.View
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.model.Book
import kotlinx.android.synthetic.main.item_book.view.*

class BookHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

  fun displayData(book: Book) = with(itemView) {
    bookAuthor.text = book.authorName
    bookDescription.text = book.text
  }
}