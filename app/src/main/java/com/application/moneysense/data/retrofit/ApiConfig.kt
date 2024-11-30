package com.application.moneysense.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private const val BASE_URL = "https://capstonebangkit-162985463576.asia-southeast1.run.app/"
        private const val API_KEY = "12345" // Ganti dengan API key Anda yang benar

        fun getApiService(): ApiService {
            // Interceptor untuk menambahkan API key ke setiap request
            val apiKeyInterceptor = Interceptor { chain ->
                val originalRequest = chain.request()
                val requestWithApiKey = originalRequest.newBuilder()
                    .addHeader("apikey", API_KEY) // Tambahkan API key di header
                    .build()
                chain.proceed(requestWithApiKey)
            }

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
