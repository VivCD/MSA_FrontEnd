package com.example.bikechat2.data.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikechat2.data.api.ApiResponse
import com.example.bikechat2.data.api.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupViewModel : ViewModel() {

    private val _groupList = MutableLiveData<List<Group>>()
    val groupList: LiveData<List<Group>> get() = _groupList
    private val _groupNames = MutableLiveData<List<String>>(emptyList())
    val groupNames: LiveData<List<String>> get() = _groupNames

    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl("https://a5e0-2a02-2f0a-e212-6d00-b0a1-f0df-1e2-4e75.ngrok-free.app") // Adjust this base URL to your backend
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
                                _groupList.value = userGroups
                                val groupNamesList = userGroups.map { it.groupName }
                                _groupNames.value = groupNamesList as List<String>?
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









}
//wow2