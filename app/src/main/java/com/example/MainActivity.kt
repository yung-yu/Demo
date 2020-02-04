package com.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.biometric.BiometricDemoActivity
import com.example.book.BookExampleActivity
import com.example.vision.QrCodeVisionDemoActivity
import com.example.vision.TextVisionDemoActivity
import com.example.vision.ZxingQrCodeVisionDemoActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = 100
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.isFitToContents = false
    }

    fun openBiometric(view: View){
        startActivity(Intent(this, BiometricDemoActivity::class.java))
    }

    fun openQrCodeVision(view: View){
        startActivity(Intent(this, QrCodeVisionDemoActivity::class.java))
    }

    fun openTextVision(view:View){
        startActivity(Intent(this, TextVisionDemoActivity::class.java))
    }

    fun openQrCodeZxing(view:View){
        startActivity(Intent(this, ZxingQrCodeVisionDemoActivity::class.java))
    }
    fun openBook(view:View){
        startActivity(Intent(this, BookExampleActivity::class.java))
    }
}