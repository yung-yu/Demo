package com.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.biometric.BiometricDemoActivity
import com.example.vision.QrCodeVisionDemoActivity
import com.example.vision.TextVisionDemoActivity
import com.example.vision.ZxingQrCodeVisionDemoActivity

class MainActivity : Activity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_main)
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
}