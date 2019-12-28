package com.maty.android.bookshelves.presentation.implementation

import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.presentation.ProfilePresenter
import com.maty.android.bookshelves.ui.profile.ProfileView
import javax.inject.Inject

class ProfilePresenterImpl @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : ProfilePresenter {

  private lateinit var view: ProfileView

  override fun setView(view: ProfileView) {
    this.view = view
  }

  override fun getProfile() {
    databaseInterface.getProfile(authenticationInterface.getUserId()) {
      val userId = authenticationInterface.getUserId()

      view.showUsername(it.username)
      view.showEmail(it.email)
    }
  }

  override fun logOut() = authenticationInterface.logOut { view.openWelcome() }
}