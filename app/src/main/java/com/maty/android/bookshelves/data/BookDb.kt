package com.maty.android.bookshelves.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.maty.android.bookshelves.model.Author
import com.maty.android.bookshelves.model.Book

/**
 * Singleton database object. TODO use Dagger to create the singleton database.
 */
@Database(entities = [Book::class, Author::class], version = 1)
abstract class BookDb : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        private var instance: BookDb? = null
        @Synchronized
        fun get(context: Context): BookDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        BookDb::class.java, "BookDatabase")
                        .build()
            }
            return instance!!
        }
    }
}