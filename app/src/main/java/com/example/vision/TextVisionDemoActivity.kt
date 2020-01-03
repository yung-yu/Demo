package com.example.vision


import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import com.example.R
import kotlinx.android.synthetic.main.activity_vision.*
import java.util.concurrent.Executors

class TextVisionDemoActivity : AppCompatActivity() {
    companion object {
        const val TAG = "TextVision"
        const val REQUEST_CAMERA_PERMISSION = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_vision)
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED){
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA) , REQUEST_CAMERA_PERMISSION)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
        CameraX.unbindAll()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        startCamera()
    }
    var textValue:String? = null
    private fun startCamera(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            return
        }
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
        }.build()
        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener { previewOutput ->
            view_finder.surfaceTexture = previewOutput.surfaceTexture
        }
        val imageAnalysisConfig = ImageAnalysisConfig.Builder()
            .build()
        val imageAnalysis = ImageAnalysis(imageAnalysisConfig)

        val qrCodeAnalyzer = TextAnalyzer { visionText ->
            if(textValue != visionText.text){
                textValue = visionText.text
                runOnUiThread {
                    text?.text = textValue
                }
            }
        }
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), qrCodeAnalyzer)
        CameraX.bindToLifecycle(this as LifecycleOwner, preview, imageAnalysis)
    }

}