package com.maty.android.bookshelves.ui.books.all

interface FloatingActionMenuView {

    fun build()

    fun setFirstAction(title : String, icon : Int, onClick : () -> Unit): FloatingActionMenuView

    fun setSecondAction(title : String, icon : Int, onClick : () -> Unit): FloatingActionMenuView

    fun hide()

    fun show()

    fun close()
}