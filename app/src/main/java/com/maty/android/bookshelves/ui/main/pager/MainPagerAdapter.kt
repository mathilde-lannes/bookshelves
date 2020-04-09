package com.maty.android.bookshelves.ui.main.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class MainPagerAdapter(manager: FragmentActivity) : FragmentStateAdapter(manager) {

  private val pages = mutableListOf<Fragment>()

  fun setPages(data: List<Fragment>) {
    pages.clear()
    pages.addAll(data)
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int = pages.size

  override fun createFragment(position: Int): Fragment = pages[position]

}