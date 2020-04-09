package com.maty.android.bookshelves

import android.app.Application
import com.intmainreturn00.grapi.grapi
import com.maty.android.bookshelves.di.AppComponent
import com.maty.android.bookshelves.di.DaggerAppComponent

class App : Application() {

  companion object {
    lateinit var instance: App
      private set

    val component: AppComponent by lazy { DaggerAppComponent.builder().build() }
  }

  override fun onCreate() {
    super.onCreate()
    instance = this

    grapi.init(this, BuildConfig.goodreadsKey, BuildConfig.goodreadsSecret, BuildConfig.goodreadsCallback)
  }
}