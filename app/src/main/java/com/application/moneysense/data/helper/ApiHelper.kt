package com.application.moneysense.data.helper

import com.application.moneysense.data.model.HistoryResponse
import com.application.moneysense.data.model.PredictResponse
import com.application.moneysense.data.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun requestPrediction(
    input: MultipartBody.Part,
    userId: RequestBody,
    onSuccess: (PredictResponse) -> Unit,
    onError: (String) -> Unit
) {
    ApiConfig.Companion.getApiService().getPrediction(input, userId).enqueue(object : Callback<PredictResponse> {
        override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
            if (response.isSuccessful) {
                response.body()?.let(onSuccess)
            } else {
                onError("Failed: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
            onError("Error: ${t.message}")
        }
    })
}

fun requestHistory(
    userId: Int,
    onSuccess: (HistoryResponse) -> Unit,
    onError: (String) -> Unit
) {
    ApiConfig.Companion.getApiService().getUserHistory(userId).enqueue(object : Callback<HistoryResponse> {
        override fun onResponse(call: Call<HistoryResponse>, response: Response<HistoryResponse>) {
            if (response.isSuccessful) {
                response.body()?.let(onSuccess)
            } else {
                onError("Failed: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
            onError("Error: ${t.message}")
        }
    })
}
