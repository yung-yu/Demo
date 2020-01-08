package com.example.vision
import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer


class ZxingQrCodeAnalyzer(
    private val onQrCodesDetected: (qrCodes:Result) -> Unit
) : ImageAnalysis.Analyzer {
    private val reader: MultiFormatReader = MultiFormatReader()

    init {
        val map = mapOf<DecodeHintType, Collection<BarcodeFormat>>(
            Pair(DecodeHintType.POSSIBLE_FORMATS, arrayListOf(BarcodeFormat.QR_CODE))
        )
        reader.setHints(map)
    }

    override fun analyze(image: ImageProxy, rotationDegrees: Int) {
        image.image?.let {
            if ((it.format == ImageFormat.YUV_420_888
                        || it.format == ImageFormat.YUV_422_888
                        || it.format == ImageFormat.YUV_444_888)
                && it.planes.size == 3) {
                val buffer = it.planes[0].buffer
                val bytes = ByteArray(buffer.capacity())
                buffer.get(bytes)
                val rotatedImage = RotatedImage(bytes, image.width, image.height)

                rotateImageArray(rotatedImage, rotationDegrees)

                val source = PlanarYUVLuminanceSource(
                    rotatedImage.byteArray,
                    rotatedImage.width, rotatedImage.height, 0, 0,
                    rotatedImage.width,
                    rotatedImage.height,
                    false
                )
                val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
                try {
                    val result = reader.decode(binaryBitmap)
                    onQrCodesDetected(result)
                } catch (e: NotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }
    private fun rotateImageArray(imageToRotate: RotatedImage, rotationDegrees: Int) {
        if (rotationDegrees == 0) return // no rotation
        if (rotationDegrees % 90 != 0) return // only 90 degree times rotations

        val width = imageToRotate.width
        val height = imageToRotate.height

        val rotatedData = ByteArray(imageToRotate.byteArray.size)
        for (y in 0 until height) { // we scan the array by rows
            for (x in 0 until width) {
                when (rotationDegrees) {
                    90 -> rotatedData[x * height + height - y - 1] =
                        imageToRotate.byteArray[x + y * width] // Fill from top-right toward left (CW)
                    180 -> rotatedData[width * (height - y - 1) + width - x - 1] =
                        imageToRotate.byteArray[x + y * width] // Fill from bottom-right toward up (CW)
                    270 -> rotatedData[y + x * height] =
                        imageToRotate.byteArray[y * width + width - x - 1] // The opposite (CCW) of 90 degrees
                }
            }
        }

        imageToRotate.byteArray = rotatedData

        if (rotationDegrees != 180) {
            imageToRotate.height = width
            imageToRotate.width = height
        }
    }
}

private data class RotatedImage(var byteArray: ByteArray, var width: Int, var height: Int)
