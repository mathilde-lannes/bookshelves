package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.common.isEmailValid
import com.maty.android.bookshelves.common.isPasswordValid
import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.model.LoginRequest
import com.maty.android.bookshelves.presentation.LoginPresenter
import com.maty.android.bookshelves.ui.login.LoginView
import javax.inject.Inject

class LoginPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface
) : LoginPresenter {

  private lateinit var view: LoginView

  private val loginRequest = LoginRequest()

  override fun setView(view: LoginView) {
    this.view = view
  }

  override fun onLoginTapped() {
    if (loginRequest.isValid()) {
      authentication.login(loginRequest.email, loginRequest.password) { isSuccess ->
        if (isSuccess) {
          view.onLoginSuccess()
        } else {
          view.showLoginError()
        }
      }
    }
  }

  override fun onEmailChanged(email: String) {
    loginRequest.email = email

    if (!isEmailValid(email)) {
      view.showEmailError()
    }
  }

  override fun onPasswordChanged(password: String) {
    loginRequest.password = password

    if (!isPasswordValid(password)) {
      view.showPasswordError()
    }
  }
}

