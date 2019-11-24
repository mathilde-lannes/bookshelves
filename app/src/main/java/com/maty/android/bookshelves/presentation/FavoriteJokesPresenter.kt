package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.model.Joke
import com.maty.android.bookshelves.ui.jokes.favorite.FavoriteView

interface FavoriteJokesPresenter : BasePresenter<FavoriteView> {

  fun getFavoriteJokes()

  fun onFavoriteButtonTapped(joke: Joke)
}