package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.model.Joke
import com.maty.android.bookshelves.presentation.AllJokesPresenter
import com.maty.android.bookshelves.ui.jokes.all.AllJokesView
import javax.inject.Inject

class AllJokesPresenterImpl @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : AllJokesPresenter {

  private lateinit var view: AllJokesView

  override fun setView(view: AllJokesView) {
    this.view = view
  }

  override fun viewReady() {
    databaseInterface.getFavoriteJokes(authenticationInterface.getUserId()) { onFavoriteJokesResult(it) }
    getAllJokes()
  }

  private fun onFavoriteJokesResult(favoriteJokes: List<Joke>) = view.setFavoriteJokesIds(favoriteJokes.map { it.id })

  override fun getAllJokes() = databaseInterface.listenToJokes { view.addJoke(it) }

  override fun onFavoriteButtonTapped(joke: Joke) {
    databaseInterface.changeJokeFavoriteStatus(joke, authenticationInterface.getUserId())
  }
}

