package com.maty.android.bookshelves.ui.jokes.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.favoriteJokesPresenter
import com.maty.android.bookshelves.model.Joke
import com.maty.android.bookshelves.ui.jokes.favorite.list.FavoriteJokeAdapter
import kotlinx.android.synthetic.main.fragment_jokes.*

class FavoriteJokesFragment : Fragment(), FavoriteView {

  private val presenter by lazy { favoriteJokesPresenter() }
  private val adapter by lazy { FavoriteJokeAdapter(presenter::onFavoriteButtonTapped) }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_jokes, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    presenter.setView(this)
    initUi()

    presenter.getFavoriteJokes()
  }

  private fun initUi() {
    jokes.layoutManager = LinearLayoutManager(activity)
    jokes.setHasFixedSize(true)
    jokes.adapter = adapter
  }

  override fun showNoDataDescription() {
    noItems.visibility = View.VISIBLE
  }

  override fun hideNoDataDescription() {
    noItems.visibility = View.GONE
  }

  override fun clearItems() = adapter.clear()

  override fun showFavoriteJokes(jokes: List<Joke>) = adapter.setData(jokes)
}