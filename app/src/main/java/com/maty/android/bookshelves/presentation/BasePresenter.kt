package com.maty.android.bookshelves.presentation

interface BasePresenter<in T> {

  fun setView(view: T)
}