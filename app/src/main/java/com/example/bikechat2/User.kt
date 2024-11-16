package com.example.bikechat2

data class User(
    val userID: String? = null,
    val username: String,
    val email: String,
    val password: String,
    val bio: String? = null,
    val profilePictureUrl: String? = null
)
