package com.example.bikechat2.data.model



import android.app.Activity
import android.app.Application
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.bikechat2.data.api.ApiResponse
import com.example.bikechat2.data.api.RetrofitInstance
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.State
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val _mapData = mutableStateOf<List<UserLocation>>(emptyList())
    val mapData: State<List<UserLocation>> get() = _mapData

    private val _currentPosition = mutableStateOf<String>("Unknown")
    val currentPosition: State<String> get() = _currentPosition

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)





    fun fetchUserLocation(context: Context, username: String) {
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            getLastLocation(username)
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
        }
    }

    private fun getLastLocation(username: String) {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val userLocation = "Lat: ${location.latitude}, Long: ${location.longitude}"
                    _currentPosition.value = userLocation

                    val currentDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    updateMapCoordinates(username, location.latitude, location.longitude, currentDate)
                } else {
                    _currentPosition.value = "Location not available"
                }
            }.addOnFailureListener { e ->
                _currentPosition.value = "Failed to get location: ${e.message}"
            }
        } catch (e: SecurityException) {
            _currentPosition.value = "Permission not granted or error occurred: ${e.message}"
        }
    }

    fun updateMapCoordinates(username: String, latitude: Double, longitude: Double, currentDate: String) {
        RetrofitInstance.api.updateMapCoordinates(username, latitude, longitude, currentDate)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Log.d("MapViewModel", "API call successfull: ${response}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("MapViewModel", "API call failed with error: ${t.message}")
                    t.printStackTrace()
                }
            })
    }

    fun fetchNearbyLocations(username: String){
        RetrofitInstance.api.getNearbyLocations(username)
            .enqueue(object : Callback<List<UserLocation>>{
                override fun onResponse(
                    call: Call<List<UserLocation>?>,
                    response: Response<List<UserLocation>?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { locations ->
                            _mapData.value = locations // Set the data to list of UserLocation objects
                        }
                    } else {
                        Log.d("MapViewModel", "Failed to fetch locations: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<UserLocation>?>, t: Throwable) {
                    Log.d("MapViewModel", "API call failed with error: ${t.message}")
                    t.printStackTrace()
                }
            })
    }
}