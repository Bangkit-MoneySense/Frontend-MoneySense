package com.application.moneysense.data.retrofit

import com.application.moneysense.data.model.HistoryResponse
import com.application.moneysense.data.model.PredictResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("predict")
    fun getPrediction(
        @Part input: MultipartBody.Part,
        @Part("userId") userId: RequestBody
    ): Call<PredictResponse>

    @GET("history")
    fun getUserHistory(
        @Query("userId") userId: Int
    ): Call<HistoryResponse>
}