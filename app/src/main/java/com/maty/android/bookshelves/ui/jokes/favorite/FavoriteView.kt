package com.maty.android.bookshelves.ui.jokes.favorite

import com.maty.android.bookshelves.model.Joke

interface FavoriteView {

  fun showFavoriteJokes(jokes: List<Joke>)

  fun showNoDataDescription()

  fun hideNoDataDescription()

  fun clearItems()
}