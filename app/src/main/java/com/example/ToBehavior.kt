package com.example

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

class ToBehavior:CoordinatorLayout.Behavior<View> {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is LinearLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val h = parent.height
        val y = dependency.y
        if(y >= h/2) {
            child.y = parent.y
            val lp: CoordinatorLayout.LayoutParams =
                child.layoutParams as CoordinatorLayout.LayoutParams
            lp.height = y.toInt()
            child.layoutParams = lp
        } else {
            child.y = parent.y
            val lp: CoordinatorLayout.LayoutParams =
                child.layoutParams as CoordinatorLayout.LayoutParams
            if(lp.height != h/2) {
                lp.height = h / 2
                child.layoutParams = lp
            }
        }
        return true
    }
}