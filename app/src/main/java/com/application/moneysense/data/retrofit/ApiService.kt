package com.application.moneysense.data.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // call the predict
    @Headers("Content-Type: application/json", "apikey: 12345")
    @POST ("predict")
    fun getPrediction (@Body requestBody: PredictRequest): Call<PredictResponse>

    // for calling the history saved in API
    @GET ("history/{userId}")
    fun getUserHistory (@Path("userID") userId : Int)

    // Data classes for the request
    data class PredictRequest(
        // input image url
        val input: String,
        val userId: Int
    )

    // response of predict result
    data class PredictResponse(
        val authenticity: String,
        val currency: String
    )
}