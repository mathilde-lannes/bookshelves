package com.maty.android.bookshelves.di

import com.maty.android.bookshelves.di.module.PresentationModule
import com.maty.android.bookshelves.presentation.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresentationModule::class])
@Singleton
interface AppComponent {

  fun registerPresenter(): RegisterPresenter

  fun loginPresenter(): LoginPresenter

  fun allBooksPresenter(): AllBooksPresenter

  fun profilePresenter(): ProfilePresenter

  fun addBookPresenter(): AddBookPresenter

  fun welcomePresenter(): WelcomePresenter
}