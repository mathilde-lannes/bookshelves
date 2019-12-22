package com.maty.android.bookshelves.firebase.database

import com.intmainreturn00.grapi.Book
import com.maty.android.bookshelves.model.User

interface FirebaseDatabaseInterface {

  fun listenToBooks(onBookAdded: (Book) -> Unit)

  fun addNewBook(book: Book, onResult: (Boolean) -> Unit)

  fun getFavoriteBooks(userId: String, onResult: (List<Book>) -> Unit)

  fun changeBookFavoriteStatus(book: Book, userId: String)

  fun createUser(id: String, name: String, email: String)

  fun getProfile(id: String, onResult: (User) -> Unit)
}