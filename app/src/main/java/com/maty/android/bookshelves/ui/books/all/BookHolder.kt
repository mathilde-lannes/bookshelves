package com.maty.android.bookshelves.ui.books.all

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.ui.books.detail.BookDetailsActivity
import kotlinx.android.synthetic.main.item_book.view.*

class BookHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

  fun displayData(book: Book?) = with(itemView) {
    author.text = book?.author // TODO persist author to Room and use it
    title.text = book?.title
    Glide.with(context.applicationContext).load(book?.imageUrl).into(itemView.cover)

    itemView.setOnClickListener {
      val intent = Intent(context.applicationContext, BookDetailsActivity::class.java)
      intent.putExtra("book", book)
      context.startActivity(intent)
    }
  }
}