package com.maty.android.bookshelves.ui.welcome

import android.os.Bundle
import com.intmainreturn00.grapi.grapi
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.ScopedAppActivity
import com.maty.android.bookshelves.common.showGeneralError
import com.maty.android.bookshelves.ui.main.MainActivity
import kotlinx.coroutines.launch
import org.jetbrains.anko.browse

class WelcomeActivity : ScopedAppActivity(), WelcomeView {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)

    if (intent.data == null) { onGoodreadsSuccess() }

    launch {
      grapi.loginEnd(intent) { ok ->
        if (ok) {
          onLoginSuccess()
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
      onLoginSuccess()
    }
  }

  override fun onLoginSuccess() = startActivity(MainActivity.getLaunchIntent(this))

  override fun showLoginError() = showGeneralError(this)
}
