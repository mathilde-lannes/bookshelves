package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.ui.login.LoginView

interface LoginPresenter : BasePresenter<LoginView> {

  fun onLoginTapped()

  fun onEmailChanged(email: String)

  fun onPasswordChanged(password: String)
}