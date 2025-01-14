package com.example.bikechat2.data.api


import com.example.bikechat2.data.model.FriendRequest
import com.example.bikechat2.data.model.LoginRequest
import com.example.bikechat2.data.model.User
import retrofit2.Call
import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    public const val BASE_URL = "https://42c1-2a02-2f0a-e212-6d00-78d8-eb3e-7db8-ed09.ngrok-free.app"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
