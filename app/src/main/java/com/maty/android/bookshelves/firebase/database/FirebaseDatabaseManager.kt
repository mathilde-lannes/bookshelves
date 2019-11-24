package com.maty.android.bookshelves.firebase.database

import com.google.firebase.database.*
import com.maty.android.bookshelves.model.*
import javax.inject.Inject

private const val KEY_USER = "user"
private const val KEY_JOKE = "book"
private const val KEY_FAVORITE = "favorite"

class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase) : FirebaseDatabaseInterface {

  override fun createUser(id: String, name: String, email: String) {
    val user = User(id, name, email)

    database.reference.child(KEY_USER).child(id).setValue(user)
  }

  override fun listenToBooks(onBookAdded: (Book) -> Unit) {
    database.reference.child(KEY_JOKE)
        .orderByKey()
        .addChildEventListener(object : ChildEventListener {
          override fun onCancelled(p0: DatabaseError?) = Unit
          override fun onChildMoved(p0: DataSnapshot?, p1: String?) = Unit
          override fun onChildChanged(p0: DataSnapshot?, p1: String?) = Unit
          override fun onChildRemoved(p0: DataSnapshot?) = Unit

          override fun onChildAdded(snapshot: DataSnapshot?, p1: String?) {
            snapshot?.getValue(BookResponse::class.java)?.run {
              if (isValid()) {
                onBookAdded(mapToBook())
              }
            }
          }
        })
  }

  override fun addNewBook(book: Book, onResult: (Boolean) -> Unit) {
    val newBookReference = database.reference.child(KEY_JOKE).push()
    val newBook = book.copy(id = newBookReference.key)

    newBookReference.setValue(newBook).addOnCompleteListener { onResult(it.isSuccessful && it.isComplete) }
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

        if (oldBook!=null) {
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