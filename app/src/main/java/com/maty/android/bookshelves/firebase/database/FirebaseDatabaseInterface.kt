package com.maty.android.bookshelves.firebase.database

import com.maty.android.bookshelves.model.Joke
import com.maty.android.bookshelves.model.User

interface FirebaseDatabaseInterface {

  fun listenToJokes(onJokeAdded: (Joke) -> Unit)

  fun addNewJoke(joke: Joke, onResult: (Boolean) -> Unit)

  fun getFavoriteJokes(userId: String, onResult: (List<Joke>) -> Unit)

  fun changeJokeFavoriteStatus(joke: Joke, userId: String)

  fun createUser(id: String, name: String, email: String)

  fun getProfile(id: String, onResult: (User) -> Unit)
}