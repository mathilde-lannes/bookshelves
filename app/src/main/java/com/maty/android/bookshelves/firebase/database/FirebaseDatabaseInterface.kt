package com.maty.android.bookshelves.firebase.database

import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.model.User

interface FirebaseDatabaseInterface {

  fun listenToBooksToRead(onBookAdded: (Book) -> Unit)

  fun listenToBooksToBuy(onBookAdded: (Book) -> Unit)

  fun addNewBook(book: Book, onResult: (Boolean) -> Unit)

  fun addBookToRead(book: Book, onResult: (Boolean) -> Unit)

  fun addBookToBuy(book: Book, onResult: (Boolean) -> Unit)

  fun getFavoriteBooks(userId: String, onResult: (List<Book>) -> Unit)

  fun changeBookFavoriteStatus(book: Book, userId: String)

  fun createUser(id: String, name: String, email: String)

  fun getProfile(id: String, onResult: (User) -> Unit)

  fun getBookById(bookId: String, onResult: (Book) -> Unit)

  fun listenToBooksCurrentlyReading(onBookAdded: (Book) -> Unit)

  fun addBookCurrentlyReading(book: Book, onResult: (Boolean) -> Unit)
}