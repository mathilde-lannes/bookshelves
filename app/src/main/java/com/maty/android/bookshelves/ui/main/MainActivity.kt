package com.maty.android.bookshelves.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.ui.books.add.AddBookActivity
import com.maty.android.bookshelves.ui.books.all.AllBooksFragment
import com.maty.android.bookshelves.ui.main.pager.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  companion object {
    fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initUi()
  }

  private fun initUi() {
    val adapter = MainPagerAdapter(this)
    adapter.setPages(listOf(AllBooksFragment()))

    mainPager.adapter = adapter
    mainPager.offscreenPageLimit = 1
    mainPager.isUserInputEnabled = false

    addBook.onClick { startActivity(Intent(this, AddBookActivity::class.java)) }
  }
}