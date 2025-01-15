package com.example.bikechat2.data.model

data class User(
    val userID: String?,
    val username: String,
    val email: String,
    val password: String,
    val profilePictureUrl: String?,
    val bio: String?,
    val friends: List<String>?,
    val pendingRequests: List<String>?,
    val locationDiscoverability: Discoverability?
)

enum class Discoverability {
    EVERYONE, FRIENDS, NOONE
}


