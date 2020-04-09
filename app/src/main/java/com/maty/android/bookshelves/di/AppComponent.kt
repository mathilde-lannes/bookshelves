package com.maty.android.bookshelves.di

import com.maty.android.bookshelves.di.module.PresentationModule
import com.maty.android.bookshelves.presentation.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresentationModule::class])
@Singleton
interface AppComponent {
  fun profilePresenter(): ProfilePresenter

  fun welcomePresenter(): WelcomePresenter
}