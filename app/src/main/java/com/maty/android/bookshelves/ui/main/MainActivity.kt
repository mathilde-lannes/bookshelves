package com.maty.android.bookshelves.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.onClick
import com.maty.android.bookshelves.common.onPageChange
import com.maty.android.bookshelves.ui.addBook.AddBookActivity
import com.maty.android.bookshelves.ui.books.all.AllBooksFragment
import com.maty.android.bookshelves.ui.main.pager.MainPagerAdapter
import com.maty.android.bookshelves.ui.profile.ProfileFragment
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
    val adapter = MainPagerAdapter(supportFragmentManager)
    adapter.setPages(listOf(AllBooksFragment(), ProfileFragment()))

    mainPager.adapter = adapter
    mainPager.offscreenPageLimit = 2
    bottomNavigation.setOnNavigationItemSelectedListener {
      switchNavigationTab(it.order)
      true
    }

    mainPager.onPageChange { position ->
      val item = bottomNavigation.menu.getItem(position)

      bottomNavigation.selectedItemId = item.itemId
    }

    addBook.onClick { startActivity(Intent(this, AddBookActivity::class.java)) }
  }

  private fun switchNavigationTab(position: Int) = mainPager.setCurrentItem(position, true)
}