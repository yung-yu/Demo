package com.example.vision


import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata


class TextAnalyzer(private val onQrCodesDetected: (qrCodes: FirebaseVisionText) -> Unit): ImageAnalysis.Analyzer{
    override fun analyze(image: ImageProxy?, rotationDegrees: Int) {


        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        val rotation = rotationDegreesToFirebaseRotation(rotationDegrees)
        val visionImage = FirebaseVisionImage.fromMediaImage(image?.image?:return, rotation)
        detector.processImage(visionImage).addOnSuccessListener { firebaseVisionText ->
            onQrCodesDetected(firebaseVisionText)
        }
        .addOnFailureListener {
            Log.e("QrCodeAnalyzer", "something went wrong", it)
        }
    }

    private fun rotationDegreesToFirebaseRotation(rotationDegrees: Int): Int {
        return when (rotationDegrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> throw IllegalArgumentException("Not supported")
        }
    }
}