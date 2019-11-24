package com.maty.android.bookshelves.ui.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.common.onTextChanged
import com.maty.android.bookshelves.registerPresenter
import com.maty.android.bookshelves.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterView {

  private val presenter by lazy { registerPresenter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)
    presenter.setView(this)
    initUi()
  }

  private fun initUi() {
    usernameInput.onTextChanged { presenter.onUsernameChanged(it) }
    emailInput.onTextChanged { presenter.onEmailChanged(it) }
    passwordInput.onTextChanged { presenter.onPasswordChanged(it) }
    repeatPasswordInput.onTextChanged { presenter.onRepeatPasswordChanged(it) }

    registerButton.onClick { presenter.onRegisterTapped() }
  }

  override fun onRegisterSuccess() = startActivity(MainActivity.getLaunchIntent(this))

  override fun showSignUpError() {
  }

  override fun showUsernameError() {
    usernameInput.error = getString(R.string.username_error)
  }

  override fun showEmailError() {
    emailInput.error = getString(R.string.email_error)
  }

  override fun showPasswordError() {
    passwordInput.error = getString(R.string.password_error)
  }

  override fun showPasswordMatchingError() {
    repeatPasswordInput.error = getString(R.string.repeat_password_error)
  }
}