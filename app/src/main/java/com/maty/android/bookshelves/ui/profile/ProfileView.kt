package com.maty.android.bookshelves.ui.profile

interface ProfileView {

  fun showUsername(username: String)

  fun showEmail(email: String)

  fun showNumberOfBooks(booksCount: Int)

  fun openWelcome()
}