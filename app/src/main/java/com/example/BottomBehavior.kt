package com.example

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import java.util.*

class BottomBehavior:CoordinatorLayout.Behavior<View> {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency.id == R.id.bottomSheet
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val h = parent.height
        val y = dependency.y
        val dh = dependency.height
        val th = h - (y+dh)
        if(th < 0){
            child.y = parent.height.toFloat()
            val lp:CoordinatorLayout.LayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
            lp.height = 0
            child.layoutParams = lp
        } else {
            restHeightAndPosition(parent, child, dependency)
            dependency.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    restHeightAndPosition(parent, child, dependency)
                    dependency.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })

        }
        return true
    }
    private fun restHeightAndPosition(parent: CoordinatorLayout, child: View, dependency: View){
        child.y =  dependency.y+dependency.height
        val lp:CoordinatorLayout.LayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = (parent.height - (dependency.y+dependency.height)).toInt()
        child.layoutParams = lp
    }
}