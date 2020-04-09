package com.maty.android.bookshelves.ui.books.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.model.Book

class BookAdapter() : RecyclerView.Adapter<BookHolder>() {

  private val items = mutableListOf<Book>()

  override fun getItemCount() = items.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)

    return BookHolder(view)
  }

  override fun onBindViewHolder(holder: BookHolder, position: Int) {
    val book = items[position]

    holder.displayData(book)
  }

  fun addBook(book: Book) {
    items.add(book)
    notifyItemInserted(items.size - 1)
  }

  fun addBooks(books: List<Book>) {
    books.forEach { addBook(it) }
  }
}