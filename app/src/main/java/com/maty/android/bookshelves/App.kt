package com.maty.android.bookshelves

import android.app.Application
import com.google.firebase.FirebaseApp
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

    FirebaseApp.initializeApp(this)
  }
}