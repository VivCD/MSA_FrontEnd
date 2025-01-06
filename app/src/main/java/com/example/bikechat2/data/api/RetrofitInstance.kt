package com.example.bikechat2.data.api


import com.example.bikechat2.data.model.FriendRequest
import com.example.bikechat2.data.model.LoginRequest
import com.example.bikechat2.data.model.User
import retrofit2.Call
import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://eeca-2a02-2f0a-e009-8e00-7c7e-c66a-d93c-e588.ngrok-free.app"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
