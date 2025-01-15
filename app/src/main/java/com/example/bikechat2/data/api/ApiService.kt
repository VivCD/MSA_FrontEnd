package com.example.bikechat2.data.api

import com.example.bikechat2.data.model.Discoverability
import com.example.bikechat2.data.model.FriendRequest
import com.example.bikechat2.data.model.LoginRequest
import com.example.bikechat2.data.model.User
import com.example.bikechat2.data.model.UserLocation
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
    fun getUserGroups(@Query("username") username: String): Call<List<Map<String, String>>>

    @GET("/map/getData")
    fun getMapData(@Query("username") username: String): Call<List<String>>

    @PUT("/location/updateLocation")
    fun updateMapCoordinates(
        @Query("username") username: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<ApiResponse>

    @GET("/location/getNearbyLocations")
    fun getNearbyLocations(@Query("username") username: String): Call<List<UserLocation>>

    @POST("/groups/initiateCall")
    fun initiateCall(@Query("groupID") groupID: String): Call<ApiResponse>

    @POST("/groups/joinGroup")
    fun joinGroup(
        @Query("groupID") groupID: String,
        @Query("username") username: String
    ): Call<String>

    @POST("/groups/leaveGroup")
    fun leaveGroup(
        @Query("groupID") groupID: String,
        @Query("username") username: String
    ): Call<String>

    @POST("/groups/createGroup")
    fun createGroup(
        @Query("username") username: String,
        @Query("groupName") groupName: String
    ): Call<String>

    @GET("/users/getUserByUsername")
    fun getUserByUsername(
        @Query("username") username: String
    ): Call<User>

    @PUT("users/updateLocationDiscoverability")
    fun updateLocationDiscoverability(
        @Query("username") username: String,
        @Query("discoverability") discoverability: Discoverability
    ): Call<String>

    @PUT("users/updateBio")
    fun updateBio(
        @Query("username") username: String,
        @Query("bio") bio: String,
    ): Call<String>

    @PUT("users/updateEmail")
    fun updateEmail(
        @Query("username") username: String,
        @Query("email") email: String,
    ): Call<String>

    @PUT("users/updateProfilePicture")
    fun updateProfilePicture(
        @Query("username") username: String,
        @Query("profilePictureUrl") profilePictureUrl: String,
    ): Call<String>


}
