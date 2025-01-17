package com.example.bikechat2.data.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikechat2.data.api.ApiResponse
import com.example.bikechat2.data.api.ApiService
import com.example.bikechat2.data.api.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupViewModel : ViewModel() {

    var groupList = mutableStateOf<List<Group>>(emptyList())
        private set
    var groupNames = mutableStateOf<List<String>>(emptyList())
        private set

    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl("https://19ea-2a02-2f0a-e212-6d00-2d5f-f093-6ba3-803d.ngrok-free.app") // Adjust this base URL to your backend
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun fetchGroups(username: String) {
        viewModelScope.launch {
            try {
                println("Fetching groups for: $username")  // Debug

                apiService.getUserGroups(username)
                    .enqueue(object : retrofit2.Callback<List<Map<String, String>>> {
                        override fun onResponse(
                            call: Call<List<Map<String, String>>>,
                            response: retrofit2.Response<List<Map<String, String>>>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                val groups = response.body()!!
                                println("Fetched Groups: $groups")
                                val userGroups = groups.map {
                                    Group(
                                        groupName = it["groupName"] ?: "Unknown Group",
                                        groupId = it["groupId"] ?: "Unknown ID"
                                    )
                                }
                                groupList.value = userGroups.toList()
                                val groupNamesList = userGroups.map { it.groupName }
                                groupNames.value = (groupNamesList as List<String>?)!!

                            } else {
                                println(
                                    "Error fetching user groups: ${
                                        response.errorBody()?.string()
                                    }"
                                )
                            }
                        }
                        override fun onFailure(call: Call<List<Map<String, String>>>, t: Throwable) {

                            println("Failed to fetch groups: ${t.localizedMessage}")
                        }
                    })
            } catch (e: Exception) {
                println("Exception fetching user groups: ${e.localizedMessage}")
            }
        }
    }

    fun initiateCall(groupID: String, onSuccess: (String) -> Unit) {
        apiService.initiateCall(groupID).enqueue(object : retrofit2.Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val callLogID = response.body()?.message?.split(":")?.last()?.trim() ?: ""
                    println("✅ Call initiated successfully. Call Log ID: $callLogID")  // ✅ Debug Successful Response
                    onSuccess(callLogID)
                } else {
                    println("❌ Error Body: ${response.errorBody()?.string()}")  // ✅ Debug Error Response
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                println("❌ Failed to start call: ${t.localizedMessage}")
            }
        })
    }

    fun joinGroup(username: String, groupID: String) {
        RetrofitInstance.api.joinGroup(groupID, username).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    println("Group joined successfully: ${response.body()}")
                    fetchGroups(username)
                } else {
                    println("Error joining group: ${response.errorBody()?.string()}")
                }

            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Failed to join group: ${t.localizedMessage}")
            }
        })
        fetchGroups(username)
    }

    fun leaveGroup(username: String, groupID: String){
        RetrofitInstance.api.leaveGroup(groupID, username).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    println("Group left successfully: ${response.body()}")
                    fetchGroups(username)
                } else {
                    println("Error leaving group: ${response.errorBody()?.string()}")
                }

            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                println("Failed to leave group: ${t.localizedMessage}")
            }
        })
        fetchGroups(username)
    }

    fun createGroup(username: String, groupName: String){
        RetrofitInstance.api.createGroup(username, groupName).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    println("Group created successfully: ${response.body()}")
                    fetchGroups(username)
                } else {
                    println("Error creating group: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                println("Failed to create group: ${t.localizedMessage}")
            }
        })
        fetchGroups(username)
    }
}