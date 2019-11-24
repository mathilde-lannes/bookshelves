package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.ui.addJoke.AddJokeView

interface AddJokePresenter : BasePresenter<AddJokeView> {

  fun addJokeTapped()

  fun onJokeTextChanged(jokeText: String)
}