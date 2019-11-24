package com.maty.android.bookshelves.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.ui.login.LoginActivity
import com.maty.android.bookshelves.ui.main.MainActivity
import com.maty.android.bookshelves.ui.register.RegisterActivity
import com.maty.android.bookshelves.welcomePresenter
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(), WelcomeView {

  private val presenter by lazy { welcomePresenter() }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)
    presenter.setView(this)

    presenter.viewReady()

    registerButton.onClick { startActivity(Intent(this, RegisterActivity::class.java)) }
    loginButton.onClick { startActivity(Intent(this, LoginActivity::class.java)) }
  }

  override fun startMainScreen() = startActivity(MainActivity.getLaunchIntent(this))
}
