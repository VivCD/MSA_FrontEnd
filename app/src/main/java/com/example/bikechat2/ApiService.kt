package com.example.bikechat2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class ApiResponse(
    val message: String,
    val success: Boolean
)

interface ApiService {
    @POST("/users/register")
    fun registerUser(@Body user: User): Call<ApiResponse>

    @POST("/users/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<ApiResponse>
}