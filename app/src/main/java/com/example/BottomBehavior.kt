package com.example

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

class BottomBehavior:CoordinatorLayout.Behavior<View> {
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
        val dh = dependency.height
        val th = h - (y+dh) + 1
        if(th < 0){
            child.y = parent.height.toFloat()
            val lp:CoordinatorLayout.LayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
            lp.height = 0
            child.layoutParams = lp
        } else {
            child.y = y + dh
            val lp:CoordinatorLayout.LayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
            lp.height = th.toInt()
            child.layoutParams = lp
        }
        return true
    }
}