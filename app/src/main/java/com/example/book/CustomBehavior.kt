package com.example.book

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CustomBehavior:BottomSheetBehavior<View>{
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

}