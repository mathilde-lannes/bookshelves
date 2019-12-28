package com.maty.android.bookshelves.firebase.database

import com.google.firebase.database.*
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.model.BookResponse
import com.maty.android.bookshelves.model.User
import com.maty.android.bookshelves.model.UserResponse
import com.maty.android.bookshelves.model.mapToBook
import javax.inject.Inject

const val KEY_USER = "user"
const val KEY_STATUS = "status"
const val KEY_BOOK = "book"
const val KEY_TO_READ = "read"
const val KEY_READING = "reading"
const val KEY_TO_BUY = "buy"
const val KEY_FAVORITE = "favorite"

class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase) : FirebaseDatabaseInterface {

    override fun createUser(id: String, name: String, email: String) {
        val user = User(id, name, email)

        database.reference.child(KEY_USER).child(id).setValue(user)
    }

    private fun listenToBooks(collection: String, onBookAdded: (Book) -> Unit) {
        database.reference.child(KEY_BOOK)
                .orderByChild(KEY_STATUS)
                .equalTo(collection)
                .addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError?) = Unit
                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) = Unit
                    override fun onChildChanged(p0: DataSnapshot?, p1: String?) = Unit
                    override fun onChildRemoved(p0: DataSnapshot?) = Unit

                    override fun onChildAdded(snapshot: DataSnapshot?, p1: String?) {
                        snapshot?.getValue(BookResponse::class.java)?.run {
                            onBookAdded(this.mapToBook())
                        }
                    }
                })
    }

    override fun listenToBooksToRead(onBookAdded: (Book) -> Unit) {
        listenToBooks(KEY_TO_READ, onBookAdded)
    }

    override fun listenToBooksCurrentlyReading(onBookAdded: (Book) -> Unit) {
        listenToBooks(KEY_READING, onBookAdded)
    }

    override fun listenToBooksToBuy(onBookAdded: (Book) -> Unit) {
        listenToBooks(KEY_TO_BUY, onBookAdded)
    }

    override fun getBookById(bookId: String, onResult: (Book) -> Unit) {
        database.reference
                .child(KEY_BOOK)
                .child(bookId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) = Unit

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        val book = snapshot?.getValue(BookResponse::class.java)

                        book?.run { onResult(mapToBook()) }
                    }
                })
    }

    private fun addBook(book: Book, collection: String, onResult: (Boolean) -> Unit) {
        book.status = collection

        val updates = mapOf(KEY_BOOK + '/' + book.id to book)

        database.reference.updateChildren(updates)
                .addOnCompleteListener { onResult(it.isSuccessful && it.isComplete) }
    }

    override fun addNewBook(book: Book, onResult: (Boolean) -> Unit) {
        addBook(book, KEY_BOOK, onResult)
    }

    override fun addBookToRead(book: Book, onResult: (Boolean) -> Unit) {
        addBook(book, KEY_TO_READ, onResult)
    }

    override fun addBookCurrentlyReading(book: Book, onResult: (Boolean) -> Unit) {
        addBook(book, KEY_READING, onResult)
    }

    override fun addBookToBuy(book: Book, onResult: (Boolean) -> Unit) {
        addBook(book, KEY_TO_BUY, onResult)
    }

    override fun getFavoriteBooks(userId: String, onResult: (List<Book>) -> Unit) {
        database.reference
                .child(KEY_USER)
                .child(userId)
                .child(KEY_FAVORITE)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) = onResult(listOf())

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        snapshot?.run {
                            val books = children.mapNotNull { it.getValue(BookResponse::class.java) }

                            onResult(books.map(BookResponse::mapToBook))
                        }
                    }
                })
    }

    override fun changeBookFavoriteStatus(book: Book, userId: String) {
        val reference = database.reference
                .child(KEY_USER)
                .child(userId)
                .child(KEY_FAVORITE)
                .child(book.id)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {}

            override fun onDataChange(snapshot: DataSnapshot?) {
                val oldBook = snapshot?.getValue(BookResponse::class.java)

                if (oldBook != null) {
                    reference.setValue(null)
                } else {
                    reference.setValue(book)
                }
            }
        })
    }

    override fun getProfile(id: String, onResult: (User) -> Unit) {
        database.reference
                .child(KEY_USER)
                .child(id)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) = Unit

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        val user = snapshot?.getValue(UserResponse::class.java)
                        val favoriteBooks = snapshot?.child(KEY_FAVORITE)?.children
                                ?.map { it?.getValue(BookResponse::class.java) }
                                ?.mapNotNull { it?.mapToBook() }
                                ?: listOf()


                        user?.run { onResult(User(id, username, email, favoriteBooks)) }
                    }
                })
    }
}