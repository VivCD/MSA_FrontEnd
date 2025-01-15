package com.example.bikechat2.data.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bikechat2.data.api.ApiResponse
import com.example.bikechat2.data.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/friends")
    fun getFriends(@Query("username") username: String): Call<List<String>>
}

class FriendsViewModel : ViewModel() {
    var friendsList = mutableStateOf<List<String>>(emptyList())
        private set

    var friendRequests = mutableStateOf<List<String>>(emptyList())
        private set

    fun fetchFriends(username: String) {
        RetrofitInstance.api.getFriends(username).enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    friendsList.value = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                println("DEBUG: API call failed with error: ${t.message}")
                t.printStackTrace()
            }
        })
    }

    fun fetchFriendRequests(username: String) {
        RetrofitInstance.api.getFriendRequests(username).enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    friendRequests.value = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                println("DEBUG: API call failed with error: ${t.message}")
                t.printStackTrace()
            }
        })
    }

    fun sendFriendRequest(senderUsername: String, receiverUsername: String) {
        RetrofitInstance.api.sendFriendRequest(senderUsername, receiverUsername)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        fetchFriendRequests(senderUsername)
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    println("DEBUG: API call failed with error: ${t.message}")
                    t.printStackTrace()
                }
            })
    }

    fun acceptFriendRequest(senderUsername: String, receiverUsername: String) {
        RetrofitInstance.api.acceptFriendRequest(receiverUsername, senderUsername)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        fetchFriends(receiverUsername)
                        fetchFriendRequests(receiverUsername)
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                    println("DEBUG: API call failed with error: ${t.message}")
                    Log.d("mymessage", "${t.message}")
                    t.printStackTrace()
                }
            })
    }


}

