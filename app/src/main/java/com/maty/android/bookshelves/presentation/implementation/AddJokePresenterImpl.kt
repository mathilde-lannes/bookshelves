package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.common.isValidJoke
import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.model.Joke
import com.maty.android.bookshelves.presentation.AddJokePresenter
import com.maty.android.bookshelves.ui.addJoke.AddJokeView
import javax.inject.Inject

class AddJokePresenterImpl @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : AddJokePresenter {

  private lateinit var view: AddJokeView

  private var jokeText = ""

  override fun setView(view: AddJokeView) {
    this.view = view
  }

  override fun addJokeTapped() {
    if (isValidJoke(jokeText)) {
      val joke = Joke("", authenticationInterface.getUserName(), authenticationInterface.getUserId(), jokeText)

      databaseInterface.addNewJoke(joke) { onAddJokeResult(it) }
    }
  }

  override fun onJokeTextChanged(jokeText: String) {
    this.jokeText = jokeText

    if (!isValidJoke(jokeText)) {
      view.showJokeError()
    } else {
      view.removeJokeError()
    }
  }

  private fun onAddJokeResult(isSuccessful: Boolean) {
    if (isSuccessful) {
      view.onJokeAdded()
    } else {
      view.showAddJokeError()
    }
  }
}