package com.maty.android.bookshelves.common

import android.view.View

inline fun View.onClick(crossinline onClickHandler: () -> Unit) {
  setOnClickListener { onClickHandler() }
}
