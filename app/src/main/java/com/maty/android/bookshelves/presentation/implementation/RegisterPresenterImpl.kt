package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.common.arePasswordsSame
import com.maty.android.bookshelves.common.isEmailValid
import com.maty.android.bookshelves.common.isPasswordValid
import com.maty.android.bookshelves.common.isUsernameValid
import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.model.RegisterRequest
import com.maty.android.bookshelves.presentation.RegisterPresenter
import com.maty.android.bookshelves.ui.register.RegisterView
import javax.inject.Inject

class RegisterPresenterImpl @Inject constructor(
    private val database: FirebaseDatabaseInterface,
    private val authentication: FirebaseAuthenticationInterface) : RegisterPresenter {

  private lateinit var view: RegisterView

  private val userData = RegisterRequest()

  override fun setView(view: RegisterView) {
    this.view = view
  }

  override fun onUsernameChanged(username: String) {
    userData.name = username

    if (!isUsernameValid(username)) {
      view.showUsernameError()
    }
  }

  override fun onEmailChanged(email: String) {
    userData.email = email

    if (!isEmailValid(email)) {
      view.showEmailError()
    }
  }

  override fun onPasswordChanged(password: String) {
    userData.password = password

    if (!isPasswordValid(password)) {
      view.showPasswordError()
    }
  }

  override fun onRepeatPasswordChanged(repeatPassword: String) {
    userData.repeatPassword = repeatPassword

    if (!arePasswordsSame(userData.password, repeatPassword)) {
      view.showPasswordMatchingError()
    }
  }

  override fun onRegisterTapped() {
    if (userData.isValid()) {
      val (name, email, password) = userData

      authentication.register(email, password, name) { isSuccessful ->
        onRegisterResult(isSuccessful, name, email)
      }
    }
  }

  private fun onRegisterResult(isSuccessful: Boolean, name: String, email: String) {
    if (isSuccessful) {
      createUser(name, email)
      view.onRegisterSuccess()
    } else {
      view.showSignUpError()
    }
  }

  private fun createUser(name: String, email: String) {
    val id = authentication.getUserId()

    database.createUser(id, name, email)
  }
}

