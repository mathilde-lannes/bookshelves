package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.presentation.ProfilePresenter
import com.maty.android.bookshelves.ui.profile.ProfileView
import javax.inject.Inject

class ProfilePresenterImpl @Inject constructor(
) : ProfilePresenter {

  private lateinit var view: ProfileView

  override fun setView(view: ProfileView) {
    this.view = view
  }

  override fun getProfile() { }

  override fun logOut() = view.openWelcome()
}