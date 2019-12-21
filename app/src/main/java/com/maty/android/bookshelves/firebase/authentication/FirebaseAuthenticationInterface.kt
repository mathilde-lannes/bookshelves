package com.maty.android.bookshelves.firebase.authentication

interface FirebaseAuthenticationInterface {

  fun login(email: String, password: String, onResult: (Boolean) -> Unit)

  fun getUserId(): String

  fun getUserName(): String

  fun logOut(onResult: () -> Unit)
}