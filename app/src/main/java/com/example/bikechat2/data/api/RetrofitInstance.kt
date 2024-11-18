package com.example.bikechat2.data.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Replace this with your ngrok URL
    private const val BASE_URL = "https://0f69-2a02-2f0b-5208-2c00-a15d-4b1b-aad-95bf.ngrok-free.app"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
