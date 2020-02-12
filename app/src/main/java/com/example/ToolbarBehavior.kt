package com.example

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

class ToolbarBehavior: CoordinatorLayout.Behavior<View> {
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
        child.y = 0f
        if(y > child.height){
            dependency.setPadding(0, 0, 0, 0)
            child.alpha = 0f
        } else {
            val alpha:Float = ((child.height - y)/child.height) * 1f
            val paddingTop:Int = (child.height - y).toInt()
            child.alpha = alpha
            dependency.setPadding(0, paddingTop, 0, 0)
        }
        return true
    }
}