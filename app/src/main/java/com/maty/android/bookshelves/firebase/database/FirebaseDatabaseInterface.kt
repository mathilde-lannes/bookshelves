package com.maty.android.bookshelves.firebase.database

import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.model.User

interface FirebaseDatabaseInterface {

  fun listenToBooksToRead(maxBooks: Int, onBookAdded: (Book) -> Unit)

  fun listenToBooksToBuy(maxBooks: Int, onBookAdded: (Book) -> Unit)

  fun addBookToRead(book: Book, onResult: (Boolean) -> Unit)

  fun addBookCurrentlyReading(book: Book, onResult: (Boolean) -> Unit)

  fun addBookToBuy(book: Book, onResult: (Boolean) -> Unit)

  fun addBookAlreadyRead(book: Book, onResult: (Boolean) -> Unit)

  fun removeBookToRead(book: Book)

  fun removeBookCurrentlyReading(book: Book)

  fun removeBookToBuy(book: Book)

  fun getFavoriteBooks(userId: String, onResult: (List<Book>) -> Unit)

  fun changeBookFavoriteStatus(book: Book, userId: String)

  fun createUser(id: String, name: String, email: String)

  fun getProfile(id: String, onResult: (User) -> Unit)

  fun listenToBooksCurrentlyReading(maxBooks: Int, onBookAdded: (Book) -> Unit)

  fun listenToBooksAlreadyRead(maxBooks: Int, onBookAdded: (Book) -> Unit)
}