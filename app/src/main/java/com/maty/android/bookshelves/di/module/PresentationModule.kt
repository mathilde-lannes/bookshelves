package com.maty.android.bookshelves.di.module

import com.maty.android.bookshelves.presentation.*
import com.maty.android.bookshelves.presentation.implementation.*
import dagger.Binds
import dagger.Module

@Module()
abstract class PresentationModule {

  @Binds
  abstract fun profilePresenter(profilePresenterImpl: ProfilePresenterImpl): ProfilePresenter

  @Binds
  abstract fun addBookPresenter(addBookPresenterImpl: AddBookPresenterImpl): AddBookPresenter

  @Binds
  abstract fun welcomePresenter(welcomePresenterImpl: WelcomePresenterImpl): WelcomePresenter
}