package com.maty.android.bookshelves.di.module

import com.maty.android.bookshelves.presentation.*
import com.maty.android.bookshelves.presentation.implementation.*
import dagger.Binds
import dagger.Module

@Module(includes = [InteractionModule::class])
abstract class PresentationModule {

  @Binds
  abstract fun allBooksPresenter(allBooksPresenterImpl: AllBooksPresenterImpl): AllBooksPresenter

  @Binds
  abstract fun profilePresenter(profilePresenterImpl: ProfilePresenterImpl): ProfilePresenter

  @Binds
  abstract fun addBookPresenter(addBookPresenterImpl: AddBookPresenterImpl): AddBookPresenter

  @Binds
  abstract fun bookDetailsPresenter(bookDetailsPresenterImpl: BookDetailsPresenterImpl): BookDetailsPresenter

  @Binds
  abstract fun welcomePresenter(welcomePresenterImpl: WelcomePresenterImpl): WelcomePresenter
}