package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.BuildConfig
import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.presentation.WelcomePresenter
import com.maty.android.bookshelves.ui.welcome.WelcomeView
import javax.inject.Inject

class WelcomePresenterImpl @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface
) : WelcomePresenter {

  private lateinit var view: WelcomeView

  override fun setView(view: WelcomeView) {
    this.view = view
  }

  override fun viewReady() {
    if (authenticationInterface.getUserId().isNotBlank()) {
      view.startMainScreen()
    }
  }

  override fun onGoodreadsTapped() {
    view.onGoodreadsSuccess()
  }

  override fun logIn() {
    authenticationInterface.login(BuildConfig.firebaseEmail, BuildConfig.firebasePassword) { isSuccess ->
      if (isSuccess) view.onLoginSuccess() else view.showLoginError()
    }
  }

  override fun afterGoodreadsAuth() {
    if (authenticationInterface.getUserId().isNotEmpty()) view.onLoginSuccess() else logIn()
  }
}