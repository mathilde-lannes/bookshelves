package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.presentation.WelcomePresenter
import com.maty.android.bookshelves.ui.welcome.WelcomeView
import javax.inject.Inject

class WelcomePresenterImpl @Inject constructor(
) : WelcomePresenter {

  private lateinit var view: WelcomeView

  override fun setView(view: WelcomeView) {
    this.view = view
  }

  override fun viewReady() {
    view.startMainScreen()
  }

  override fun onGoodreadsTapped() {
    view.onGoodreadsSuccess()
  }

  override fun logIn() {
  }

  override fun afterGoodreadsAuth() {
    view.onLoginSuccess()
  }
}