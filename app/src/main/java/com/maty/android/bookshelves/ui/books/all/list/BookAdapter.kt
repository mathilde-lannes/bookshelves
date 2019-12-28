package com.maty.android.bookshelves.ui.books.all.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.R

class BookAdapter() : RecyclerView.Adapter<BookHolder>() {

  private val items = mutableListOf<Book>()
  private val favoriteBooksIds = mutableListOf<String>()

  override fun getItemCount() = items.size

  fun setFavoriteBooksIds(ids: List<String>) {
    favoriteBooksIds.clear()
    favoriteBooksIds.addAll(ids)
    notifyDataSetChanged()
  }

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
}