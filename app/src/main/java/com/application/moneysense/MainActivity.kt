package com.application.moneysense

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.application.moneysense.data.retrofit.requestPrediction
import com.application.moneysense.data.retrofit.requestHistory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val file = File("/C:/Users/cyber/Videos/pecahan-rp-20000_20150710_204516.jpg")
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imageBody = MultipartBody.Part.createFormData("input", file.name, requestFile)
        val userIdBody = "1".toRequestBody("text/plain".toMediaTypeOrNull())

        // Test API Predict
        requestPrediction(imageBody, userIdBody,
            onSuccess = { response ->
                val data = response.data
                val message = "Currency: ${data?.currency}, Authenticity: ${data?.authenticity}"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            },
            onError = { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        )

        // Test API History
        requestHistory(1,
            onSuccess = { response ->
                response.data?.forEach {
                    val message = "Currency: ${it.currency}, Authenticity: ${it.authenticity}"
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            },
            onError = { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        )
    }
}
