package com.maty.android.bookshelves.ui.login

interface LoginView {

  fun showPasswordError()

  fun showEmailError()

  fun onLoginSuccess()

  fun showLoginError()
}