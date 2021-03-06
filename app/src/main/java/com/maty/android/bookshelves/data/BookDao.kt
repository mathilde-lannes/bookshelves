package com.maty.android.bookshelves.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maty.android.bookshelves.model.Book

/**
 * Database Access Object for the Book database.
 */
@Dao
interface BookDao {
    @Query("SELECT * FROM Book WHERE status = :status ORDER BY entryDate DESC")
    fun booksByStatus(status : String): LiveData<List<Book>>

    @Query("SELECT * FROM Book WHERE status = :status ORDER BY entryDate DESC LIMIT :limit")
    fun booksByStatus(status : String, limit: Int): LiveData<List<Book>>

    @Insert
    fun insert(books: List<Book>)

    @Insert
    fun insert(book: Book)

    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)
}