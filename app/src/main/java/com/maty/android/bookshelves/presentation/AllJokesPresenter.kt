package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.model.Joke
import com.maty.android.bookshelves.ui.jokes.all.AllJokesView

interface AllJokesPresenter : BasePresenter<AllJokesView> {

  fun viewReady()

  fun getAllJokes()

  fun onFavoriteButtonTapped(joke: Joke)
}