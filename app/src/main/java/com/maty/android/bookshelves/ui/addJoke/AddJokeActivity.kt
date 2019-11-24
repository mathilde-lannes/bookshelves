package com.maty.android.bookshelves.ui.addJoke

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.addJokePresenter
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.common.onTextChanged
import com.maty.android.bookshelves.common.showGeneralError
import kotlinx.android.synthetic.main.activity_add_joke.*

class AddJokeActivity : AppCompatActivity(), AddJokeView {

  private val presenter by lazy { addJokePresenter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_joke)
    presenter.setView(this)

    initUi()
  }

  private fun initUi() {
    jokeDescription.onTextChanged { presenter.onJokeTextChanged(it) }
    addJoke.onClick { presenter.addJokeTapped() }
  }

  override fun showJokeError() {
    jokeDescription.error = getString(R.string.joke_error)
  }

  override fun removeJokeError() {
    jokeDescription.error = null
  }

  override fun onJokeAdded() = finish()

  override fun showAddJokeError() = showGeneralError(this)
}