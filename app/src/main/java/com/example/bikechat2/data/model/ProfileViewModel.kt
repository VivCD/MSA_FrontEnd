package com.example.bikechat2.data.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bikechat2.data.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import retrofit2.Retrofit

class ProfileViewModel : ViewModel() {

    var user = mutableStateOf(
        User(
            userID = "",
            username = "",
            email = "",
            password = "",
            profilePictureUrl = null,
            bio = null,
            friends = emptyList(),
            pendingRequests = emptyList(),
            locationDiscoverability = Discoverability.EVERYONE
        )
    )

    fun getUserData(username: String) {
        RetrofitInstance.api.getUserByUsername(username).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val fetchedUser = response.body()
                    if (fetchedUser != null) {
                        user.value = fetchedUser
                        println("User data fetched successfully: ${user.value.username}")
                    }
                } else {
                    println("Error fetching user data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("Failed to fetch user data: ${t.localizedMessage}")
            }
        })
    }

    fun updateLocationDiscoverability(username: String, discoverability: Discoverability){
        RetrofitInstance.api.updateLocationDiscoverability(username, discoverability).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    println("Field updated successfully: ${response.body()}")
                } else {
                    println("Error updating field: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                println("Failed to update field: ${t.localizedMessage}")
            }
        })
    }

    fun updateBio(username: String, bio: String){
        RetrofitInstance.api.updateBio(username, bio).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    println("Field updated successfully: ${response.body()}")
                } else {
                    println("Error updating field: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                println("Failed to update field: ${t.localizedMessage}")
            }
        })
    }

    fun updateEmail(username: String, email: String){
        RetrofitInstance.api.updateEmail(username, email).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    println("Field updated successfully: ${response.body()}")
                } else {
                    println("Error updating field: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                println("Failed to update field: ${t.localizedMessage}")
            }
        })
    }

    fun updateProfilePicture(username: String, profilePictureUrl: String){
        RetrofitInstance.api.updateProfilePicture(username, profilePictureUrl).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    println("Field updated successfully: ${response.body()}")
                } else {
                    println("Error updating field: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                println("Failed to update field: ${t.localizedMessage}")
            }
        })
    }
}