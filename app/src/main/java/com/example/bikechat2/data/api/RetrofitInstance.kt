package com.example.bikechat2.data.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Replace this with your ngrok URL
    private const val BASE_URL = "https://f351-2a02-2f0a-e009-2f00-f030-a5ca-6bab-5807.ngrok-free.app"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
