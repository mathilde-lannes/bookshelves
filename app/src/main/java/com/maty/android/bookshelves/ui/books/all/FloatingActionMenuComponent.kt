package com.maty.android.bookshelves.ui.books.all

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.maty.android.bookshelves.R
import com.maty.android.bookshelves.common.onClick
import kotlinx.android.synthetic.main.floating_action_menu.view.*


class FloatingActionMenuComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var isOpen = false
    private var hasFirstAction = false
    private var hasSecondAction = false

    init {
        LayoutInflater.from(context).inflate(R.layout.floating_action_menu, this, true)
    }

    fun build() {
        if (!hasFirstAction) {
            throw IllegalStateException("You need to set at least one action before building the component.")
        }
        if (!hasSecondAction) {
            onlyAction.visibility = View.VISIBLE
            mainAction.visibility = View.GONE
        } else {
            addHandlersForMultipleActions()
        }
    }

    fun setFirstAction(title : String, icon : Int, onClick : () -> Unit): FloatingActionMenuComponent {
        hasFirstAction = true
        subAction1Title.text = title
        subAction1.setImageResource(icon)
        subAction1.onClick { onClick() }

        onlyAction.text = title
        onlyAction.icon = resources.getDrawable(icon, null)
        onlyAction.onClick { onClick() }

        return this
    }

    fun setSecondAction(title : String, icon : Int, onClick : () -> Unit): FloatingActionMenuComponent {
        hasSecondAction = true
        subAction2Title.text = title
        subAction2.setImageResource(icon)
        subAction2.onClick { onClick() }

        return this
    }

    private fun addHandlersForMultipleActions() {
        val clockAnimation = AnimationUtils.loadAnimation(context.applicationContext, R.anim.fab_rotate_clock)
        val antiClockAnimation = AnimationUtils.loadAnimation(context.applicationContext, R.anim.fab_rotate_anticlock)

        mainAction.setOnClickListener {
            if (isOpen) {
                subAction2Title.visibility = View.INVISIBLE
                subAction2.hide()

                subAction1Title.visibility = View.INVISIBLE
                subAction1.hide()

                mainAction.startAnimation(antiClockAnimation)
                isOpen = false
            } else {
                if (subAction2Title.text.toString().isNotEmpty()) subAction2Title.visibility = View.VISIBLE
                subAction2.show()

                if (subAction1Title.text.toString().isNotEmpty()) subAction1Title.visibility = View.VISIBLE
                subAction1.show()

                mainAction.startAnimation(clockAnimation)
                isOpen = true
            }
        }

    }

    fun hide() {
        if (!hasSecondAction) {
            onlyAction.hide()
        } else {
            if (isOpen) {
                mainAction.startAnimation(AnimationUtils.loadAnimation(context.applicationContext, R.anim.fab_rotate_anticlock))
                isOpen = false
            }

            mainAction.hide()
            subAction1Title.visibility = View.GONE
            subAction1.hide()
            subAction2Title.visibility = View.GONE
            subAction2.hide()
        }
    }

    fun show() {
        if (hasSecondAction)  mainAction.show() else onlyAction.show()
    }

    fun close() {
        if (hasSecondAction) {
            subAction2Title.visibility = View.INVISIBLE
            subAction2.hide()

            subAction1Title.visibility = View.INVISIBLE
            subAction1.hide()

            mainAction.startAnimation(AnimationUtils.loadAnimation(context.applicationContext, R.anim.fab_rotate_anticlock))
            isOpen = false
        }
    }
}