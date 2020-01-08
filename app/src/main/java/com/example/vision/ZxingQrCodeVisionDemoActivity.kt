package com.example.vision


import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import com.example.R
import kotlinx.android.synthetic.main.activity_vision.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

class ZxingQrCodeVisionDemoActivity : AppCompatActivity() {
    companion object {
        const val TAG = "QrCodeVision"
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
    var qrcodeValue:String? = null
    private fun startCamera(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            return
        }
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(1920, 1080))
        }.build()
        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener { previewOutput ->
            view_finder.surfaceTexture = previewOutput.surfaceTexture
        }
        val imageAnalysisConfig = ImageAnalysisConfig.Builder()
            .build()
        val imageAnalysis = ImageAnalysis(imageAnalysisConfig)

        val qrCodeAnalyzer = ZxingQrCodeAnalyzer{ result ->
            if(qrcodeValue != result.text){
                qrcodeValue = result.text
                runOnUiThread {
                    text?.text = qrcodeValue
                }
            }
        }
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), qrCodeAnalyzer)
        CameraX.bindToLifecycle(this as LifecycleOwner, preview, imageAnalysis)
    }

}