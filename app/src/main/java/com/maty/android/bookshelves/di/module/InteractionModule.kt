package com.maty.android.bookshelves.di.module

import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationInterface
import com.maty.android.bookshelves.firebase.authentication.FirebaseAuthenticationManager
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseInterface
import com.maty.android.bookshelves.firebase.database.FirebaseDatabaseManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [FirebaseModule::class])
@Singleton
abstract class InteractionModule {

  @Binds
  abstract fun authentication(authentication: FirebaseAuthenticationManager): FirebaseAuthenticationInterface

  @Binds
  abstract fun database(database: FirebaseDatabaseManager): FirebaseDatabaseInterface
}