package com.maty.android.bookshelves.ui.welcome

import android.os.Bundle
import com.intmainreturn00.grapi.grapi
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.ScopedAppActivity
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.common.showGeneralError
import com.maty.android.bookshelves.ui.main.MainActivity
import com.maty.android.bookshelves.welcomePresenter
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.browse

class WelcomeActivity : ScopedAppActivity(), WelcomeView {

  private val presenter by lazy { welcomePresenter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)
    presenter.setView(this)

    presenter.viewReady()

    goodreads.onClick { presenter.onGoodreadsTapped() }

    launch {
      grapi.loginEnd(intent) { ok ->
        if (ok) {
          presenter.afterGoodreadsAuth()
        }
      }
    }
  }

  override fun startMainScreen() = startActivity(MainActivity.getLaunchIntent(this))

  override fun onGoodreadsSuccess() {
    if (!grapi.isLoggedIn()) {
      launch {
        grapi.loginStart()
        browse(grapi.getAuthorizationUrl())
      }
    } else {
      presenter.afterGoodreadsAuth()
    }
  }

  override fun onLoginSuccess() = startActivity(MainActivity.getLaunchIntent(this))

  override fun showLoginError() = showGeneralError(this)
}
