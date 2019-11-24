package com.maty.android.bookshelves.ui.jokes.all

import com.maty.android.bookshelves.model.Joke

interface AllJokesView {

  fun showNoDataDescription()

  fun hideNoDataDescription()

  fun setFavoriteJokesIds(favoriteJokesIds: List<String>)

  fun addJoke(joke: Joke)
}