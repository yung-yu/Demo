package com.example.book

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

class CustomBehavior:CoordinatorLayout.Behavior<View> {
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
        val helfHeight = parent.height / 2
        val scroll = parent.height - dependency.y
        if(scroll <= helfHeight){
            child.translationY = -scroll
        }
        return true
    }
}