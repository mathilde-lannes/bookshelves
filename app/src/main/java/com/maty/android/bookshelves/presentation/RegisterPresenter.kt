package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.ui.register.RegisterView

interface RegisterPresenter : BasePresenter<RegisterView> {

  fun onUsernameChanged(username: String)

  fun onEmailChanged(email: String)

  fun onPasswordChanged(password: String)

  fun onRepeatPasswordChanged(repeatPassword: String)

  fun onRegisterTapped()

}