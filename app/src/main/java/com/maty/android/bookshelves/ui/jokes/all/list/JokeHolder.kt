package com.maty.android.bookshelves.ui.jokes.all.list

import android.support.v7.widget.RecyclerView
import android.view.View
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.model.Joke
import kotlinx.android.synthetic.main.item_joke.view.*

class JokeHolder(
    itemView: View,
    private inline val onFavoriteClickHandler: (Joke) -> Unit
) : RecyclerView.ViewHolder(itemView) {

  fun displayData(joke: Joke) = with(itemView) {
    favoriteButton.onClick { onFavoriteClickHandler(joke) }

    jokeAuthor.text = joke.authorName
    jokeDescription.text = joke.text

    favoriteButton.setImageResource(if(joke.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border)
  }
}