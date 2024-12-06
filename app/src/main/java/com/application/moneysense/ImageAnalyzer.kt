package com.application.moneysense

import android.graphics.ImageFormat
import android.graphics.YuvImage
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.application.moneysense.data.retrofit.requestPrediction
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class ImageAnalyzer(private val onResult: (String) -> Unit) : ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        val byteArray = image.toByteArray()
        image.close()

        val requestFile = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("input", "image.jpg", requestFile)
        val userId = "1".toRequestBody("text/plain".toMediaTypeOrNull())

        requestPrediction(imagePart, userId,
            onSuccess = { response ->
                val result = "Currency: ${response.data?.currency}, Authenticity: ${response.data?.authenticity}"
                onResult(result)
            },
            onError = { error ->
                onResult("Error: $error")
            }
        )
    }

    private fun ImageProxy.toByteArray(): ByteArray {
        val yBuffer = planes[0].buffer
        val vuBuffer = planes[2].buffer

        val ySize = yBuffer.remaining()
        val vuSize = vuBuffer.remaining()

        val nv21 = ByteArray(ySize + vuSize)

        yBuffer.get(nv21, 0, ySize)
        vuBuffer.get(nv21, ySize, vuSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(android.graphics.Rect(0, 0, width, height), 100, out)

        return out.toByteArray()
    }
}
