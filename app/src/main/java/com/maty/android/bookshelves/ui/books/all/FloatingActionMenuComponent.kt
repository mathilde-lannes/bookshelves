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

    var isOpen = false
    var hasFirstAction = false
    var hasSecondAction = false

    init {
        LayoutInflater.from(context).inflate(R.layout.floating_action_menu, this, true)
        addHandlers()
    }

    fun initFirstAction(title : String, icon : Int, onClick : () -> Unit) {
        hasFirstAction = true
        subAction1Title.text = title
        subAction1.setImageResource(icon)
        subAction1.onClick { onClick() }

    }

    fun initSecondAction(title : String, icon : Int, onClick : () -> Unit) {
        hasSecondAction = true
        subAction2Title.text = title
        subAction2.setImageResource(icon)
        subAction2.onClick { onClick() }

    }

    private fun addHandlers() {
        val closeAnimation = AnimationUtils.loadAnimation(context.applicationContext, R.anim.fab_close);
        val openAnimation = AnimationUtils.loadAnimation(context.applicationContext, R.anim.fab_open);
        val clockAnimation = AnimationUtils.loadAnimation(context.applicationContext, R.anim.fab_rotate_clock);
        val antiClockAnimation = AnimationUtils.loadAnimation(context.applicationContext, R.anim.fab_rotate_anticlock);

        mainAction.setOnClickListener {
            if (isOpen) {
                if (hasSecondAction) {
                    subAction2Title.visibility = View.INVISIBLE
                    subAction2.startAnimation(closeAnimation)
                    subAction2.isClickable = false
                }

                if (hasFirstAction) {
                    subAction1Title.visibility = View.INVISIBLE
                    subAction1.startAnimation(closeAnimation)
                    subAction1.isClickable = false
                }

                mainAction.startAnimation(antiClockAnimation)
                isOpen = false
            } else {
                if (hasSecondAction) {
                    subAction2Title.visibility = View.VISIBLE
                    subAction2.startAnimation(openAnimation)
                    subAction2.isClickable = true
                }

                if (hasFirstAction) {
                    subAction1Title.visibility = View.VISIBLE
                    subAction1.startAnimation(openAnimation)
                    subAction1.isClickable = true
                }

                mainAction.startAnimation(clockAnimation)
                isOpen = true
            }
        }

    }
}