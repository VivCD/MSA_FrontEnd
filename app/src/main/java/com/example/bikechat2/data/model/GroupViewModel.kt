package com.example.bikechat2.data.model

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

    private val _groupList = MutableStateFlow<List<String>>(emptyList())
    val groupList = _groupList.asStateFlow()

    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl("https://2eef-2a02-2f0b-5309-9100-3807-e23-d18d-196.ngrok-free.app") // Adjust this base URL to your backend
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun fetchGroups(username: String) {
        viewModelScope.launch {
            try {
                println("Fetching groups for: $username")  // Debug

                apiService.getUserGroups(username)
                    .enqueue(object : retrofit2.Callback<List<String>> {
                        override fun onResponse(
                            call: Call<List<String>>,
                            response: retrofit2.Response<List<String>>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                println("Fetched Groups: ${response.body()}")  // Debug
                                _groupList.value = response.body()!!
                            } else {
                                println(
                                    "Error fetching user groups: ${
                                        response.errorBody()?.string()
                                    }"
                                )
                            }
                        }

                        override fun onFailure(call: Call<List<String>>, t: Throwable) {
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