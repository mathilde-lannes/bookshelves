package com.maty.android.bookshelves.ui.jokes.all.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.model.Joke

class JokeAdapter(
    private val onFavoriteClickHandler: (Joke) -> Unit
) : RecyclerView.Adapter<JokeHolder>() {

  private val items = mutableListOf<Joke>()
  private val favoriteJokesIds = mutableListOf<String>()

  override fun getItemCount() = items.size

  fun setFavoriteJokesIds(ids: List<String>) {
    favoriteJokesIds.clear()
    favoriteJokesIds.addAll(ids)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_joke, parent, false)

    return JokeHolder(view, onFavoriteClickHandler)
  }

  override fun onBindViewHolder(holder: JokeHolder, position: Int) {
    val joke = items[position].apply { isFavorite = id in favoriteJokesIds }

    holder.displayData(joke)
  }

  fun addJoke(joke: Joke) {
    items.add(joke)
    notifyItemInserted(items.size - 1)
  }
}