package com.maty.android.bookshelves.presentation

import com.maty.android.bookshelves.ui.profile.ProfileView

interface ProfilePresenter : BasePresenter<ProfileView> {

  fun getProfile()

  fun logOut()
}