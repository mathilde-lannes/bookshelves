package com.maty.android.bookshelves.firebase.database

import com.google.firebase.database.*
import com.maty.android.bookshelves.model.Book
import com.maty.android.bookshelves.model.BookResponse
import com.maty.android.bookshelves.model.User
import com.maty.android.bookshelves.model.UserResponse
import com.maty.android.bookshelves.model.mapToBook
import javax.inject.Inject
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener



const val KEY_USER = "user"
const val KEY_TO_READ = "read"
const val KEY_ALREADY_READ = "already_read"
const val KEY_READING = "reading"
const val KEY_TO_BUY = "buy"
const val KEY_FAVORITE = "favorite"

class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase) : FirebaseDatabaseInterface {

    override fun createUser(id: String, name: String, email: String) {
        val user = User(id, name, email)

        database.reference.child(KEY_USER).child(id).setValue(user)
    }

    private fun listenToBooks(maxBooks: Int, collection: String, onBookAdded: (Book) -> Unit, onCompleted: (Boolean) -> Unit) {
        database.reference.child(collection)
                .limitToLast(maxBooks)
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

        database.reference.ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onCompleted(true)
            }
            override fun onCancelled(p0: DatabaseError) = Unit
        })
    }

    override fun listenToBooksToRead(maxBooks: Int, onBookAdded: (Book) -> Unit, onCompleted: (Boolean) -> Unit) {
        listenToBooks(maxBooks, KEY_TO_READ, onBookAdded, onCompleted)
    }

    override fun listenToBooksAlreadyRead(maxBooks: Int, onBookAdded: (Book) -> Unit, onCompleted: (Boolean) -> Unit) {
        listenToBooks(maxBooks, KEY_ALREADY_READ, onBookAdded, onCompleted)
    }

    override fun listenToBooksCurrentlyReading(maxBooks: Int, onBookAdded: (Book) -> Unit) {
        listenToBooks(maxBooks, KEY_READING, onBookAdded, {})
    }

    override fun listenToBooksToBuy(maxBooks: Int, onBookAdded: (Book) -> Unit, onCompleted: (Boolean) -> Unit) {
        listenToBooks(maxBooks, KEY_TO_BUY, onBookAdded, onCompleted)
    }

    private fun addBook(book: Book, collection: String, onResult: (Boolean) -> Unit) {
        val currentTimestamp = System.currentTimeMillis().toString()
        book.status = collection

        database.reference
                .child(collection)
                .child(currentTimestamp)
                .setValue(book)
                .addOnCompleteListener { onResult(it.isSuccessful && it.isComplete) }
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

    override fun addBookAlreadyRead(book: Book, onResult: (Boolean) -> Unit) {
        addBook(book, KEY_ALREADY_READ, onResult)
    }

    private fun removeBookFromCollection(book: Book, collection: String) {
        book.status = collection

        database.reference
                .child(collection)
                .orderByChild("id")
                .equalTo(book.id)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) = Unit

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        snapshot?.run {
                            children.forEach { database.reference
                                    .child(collection)
                                    .child(it.key)
                                    .setValue(null)
                            }
                        }
                    }
                })
    }

    override fun removeBookToRead(book: Book) {
        removeBookFromCollection(book, KEY_TO_READ)
    }

    override fun removeBookCurrentlyReading(book: Book) {
        removeBookFromCollection(book, KEY_READING)
    }

    override fun removeBookToBuy(book: Book) {
        removeBookFromCollection(book, KEY_TO_BUY)
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