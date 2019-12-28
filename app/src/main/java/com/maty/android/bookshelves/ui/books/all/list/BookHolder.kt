package com.maty.android.bookshelves.ui.books.all.list

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.detail.BookDetailsActivity
import kotlinx.android.synthetic.main.item_book.view.*

class BookHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

  fun displayData(book: Book) = with(itemView) {
    bookAuthor.text = book.authors[0].name
    bookDescription.text = book.description

    itemView.setOnClickListener {
      val intent = Intent(context, BookDetailsActivity::class.java)
      intent.putExtra("book", book)
      context.startActivity(intent)
    }
  }
}