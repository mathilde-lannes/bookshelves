package com.maty.android.bookshelves.data

import android.content.Context
import androidx.room.*
import com.maty.android.bookshelves.model.Author
import com.maty.android.bookshelves.model.Book
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Singleton database object. TODO use Dagger to create the singleton database.
 */

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let {
            LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it), ZoneId.systemDefault()
            )
        }
    }

    @TypeConverter
    fun LocalDateTimeToTimestamp(date: LocalDateTime?): Long? {
        return date?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }
}
@Database(entities = [Book::class, Author::class], version = 1)
@TypeConverters(Converters::class)
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