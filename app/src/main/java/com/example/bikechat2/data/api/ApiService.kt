package com.example.bikechat2.data.api

import com.example.bikechat2.data.model.FriendRequest
import com.example.bikechat2.data.model.LoginRequest
import com.example.bikechat2.data.model.User
import retrofit2.Call
import retrofit2.http.*

data class ApiResponse(
    val message: String,
    val success: Boolean
)

interface ApiService {

    @POST("/users/register")
    fun registerUser(@Body user: User): Call<ApiResponse>

    @POST("/users/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<ApiResponse>

    @GET("/users/getFriendsList")
    fun getFriends(@Query("usernameWhoRequestsList") username: String): Call<List<String>>

    @POST("/users/{username}/sendRequest")
    fun sendFriendRequest(
        @Path("username") username: String,
        @Query("friendUsername") friendUsername: String
    ): Call<ApiResponse>

    @POST("/users/{whoAccepts}/acceptRequest")
    fun acceptFriendRequest(
        @Path("whoAccepts") whoAccepts: String,
        @Query("usernameWhoSends") usernameWhoSends: String
    ): Call<ApiResponse>

    @POST("/users/{username}/deleteFriend")
    fun removeFriend(
        @Path("username") username: String,
        @Query("friendToBeDeleted") friendUsername: String
    ): Call<ApiResponse>


    @GET("/users/{username}/friendRequests")
    fun getFriendRequests(
        @Path("username") username: String
    ): Call<List<String>>

    // New API endpoint to fetch groups
    @GET("/groups/getByName")
    fun getGroups(@Query("groupName") groupName: String): Call<List<String>>

    @GET("/groups/getUserGroups")
    fun getUserGroups(@Query("username") username: String): Call<List<String>>

    @GET("/map/getData")
    fun getMapData(@Query("username") username: String): Call<List<String>>

    @PUT("/location/updateLocation")
    fun updateMapCoordinates(
        @Query("username") username: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("creationDate") creationDate: String
    ): Call<ApiResponse>
}
