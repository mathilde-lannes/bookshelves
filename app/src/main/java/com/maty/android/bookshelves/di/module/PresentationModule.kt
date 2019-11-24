package com.maty.android.bookshelves.di.module

import com.maty.android.bookshelves.presentation.*
import com.maty.android.bookshelves.presentation.implementation.*
import dagger.Binds
import dagger.Module

@Module(includes = [InteractionModule::class])
abstract class PresentationModule {

  @Binds
  abstract fun loginPresenter(loginPresenter: LoginPresenterImpl): LoginPresenter

  @Binds
  abstract fun registerPresenter(registerPresenter: RegisterPresenterImpl): RegisterPresenter

  @Binds
  abstract fun allJokesPresenter(allJokesPresenterImpl: AllJokesPresenterImpl): AllJokesPresenter

  @Binds
  abstract fun favoriteJokesPresenter(favoriteJokesPresenterImpl: FavoriteJokesPresenterImpl): FavoriteJokesPresenter

  @Binds
  abstract fun profilePresenter(profilePresenterImpl: ProfilePresenterImpl): ProfilePresenter

  @Binds
  abstract fun addJokePresenter(addJokePresenterImpl: AddJokePresenterImpl): AddJokePresenter

  @Binds
  abstract fun welcomePresenter(welcomePresenterImpl: WelcomePresenterImpl): WelcomePresenter
}