package com.application.moneysense.data.helper

import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.application.moneysense.data.model.PredictResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class ImageAnalyzer(private val onResult: (PredictResponse) -> Unit) : ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        try {
            val byteArray = image.toByteArray()
            image.close() // Always close the image to avoid memory leaks

            if (byteArray == null) {
                Log.e("ImageAnalyzer", "Image conversion failed, skipping analysis")
                return
            }

            val requestFile = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("input", "image.jpg", requestFile)
            val userId = "1".toRequestBody("text/plain".toMediaTypeOrNull())

            requestPrediction(imagePart, userId,
                onSuccess = { response ->
                    onResult(response)
                },
                onError = { error ->
                    Log.e("ImageAnalyzer", "Prediction request failed: ${error}")
                    onResult(PredictResponse(data = null))
                }
            )
        } catch (e: Exception) {
            Log.e("ImageAnalyzer", "Unexpected error in analyze: ${e.message}", e)
        }
    }
//        val byteArray = image.toByteArray()
//        image.close()
//
//        val requestFile = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
//        val imagePart = MultipartBody.Part.createFormData("input", "image.jpg", requestFile)
//        val userId = "1".toRequestBody("text/plain".toMediaTypeOrNull())
//
//        requestPrediction(imagePart, userId,
//            onSuccess = { response ->
//                val result = "Currency: ${response.data?.currency}, Authenticity: ${response.data?.authenticity}"
//                onResult(response)
//            },
//            onError = { error ->
//                onResult(PredictResponse(data = null))
//            }
//        )
}

private fun ImageProxy.toByteArray(): ByteArray? {
    return try {
        if (format != ImageFormat.YUV_420_888) {
            Log.e("ImageAnalyzer", "Unsupported image format: $format")
            return null
        }

        val yBuffer = planes[0].buffer
        val uBuffer = planes[1].buffer
        val vBuffer = planes[2].buffer

        val ySize = yBuffer.remaining()
        val uvSize = uBuffer.remaining() + vBuffer.remaining()
        val nv21 = ByteArray(ySize + uvSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vBuffer.remaining())
        uBuffer.get(nv21, ySize + vBuffer.remaining(), uBuffer.remaining())

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
        val outStream = ByteArrayOutputStream()
        yuvImage.compressToJpeg(android.graphics.Rect(0, 0, width, height), 100, outStream)
        outStream.toByteArray()
    } catch (e: Exception) {
        Log.e("ImageAnalyzer", "Error in toByteArray: ${e.message}", e)
        null
    }
//        val yBuffer = planes[0].buffer
//        val vuBuffer = planes[2].buffer
//
//        val ySize = yBuffer.remaining()
//        val vuSize = vuBuffer.remaining()
//
//        val nv21 = ByteArray(ySize + vuSize)
//
//        yBuffer.get(nv21, 0, ySize)
//        vuBuffer.get(nv21, ySize, vuSize)
//
//        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
//        val out = ByteArrayOutputStream()
//        yuvImage.compressToJpeg(android.graphics.Rect(0, 0, width, height), 100, out)
//
//        return out.toByteArray()
}
