package com.maty.android.bookshelves.firebase.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class FirebaseAuthenticationManager @Inject constructor(private val authentication: FirebaseAuth) : FirebaseAuthenticationInterface {

  override fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
    authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener {
      onResult(it.isComplete && it.isSuccessful)
    }
  }

  override fun getUserId(): String = authentication.currentUser?.uid ?: ""
  override fun getUserName(): String = authentication.currentUser?.displayName ?: ""

  override fun logOut(onResult: () -> Unit) {
    authentication.signOut()

    onResult()
  }
}