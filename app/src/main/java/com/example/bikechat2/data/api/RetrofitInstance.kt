package com.example.bikechat2.data.api


import com.example.bikechat2.data.model.FriendRequest
import com.example.bikechat2.data.model.LoginRequest
import com.example.bikechat2.data.model.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    public const val BASE_URL = "https://b597-2a02-2f0a-e212-6d00-b0a1-f0df-1e2-4e75.ngrok-free.app"

    private val gson: Gson = GsonBuilder()
        .setLenient()  // ✅ Allow lenient JSON parsing
        .create()

    private val client = OkHttpClient.Builder().build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))  // ✅ Use lenient Gson
            .build()
            .create(ApiService::class.java)
    }
}