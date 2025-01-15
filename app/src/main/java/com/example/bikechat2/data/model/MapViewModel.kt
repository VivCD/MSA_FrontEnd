package com.example.bikechat2.data.model



import android.R
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val _mapData = mutableStateOf<List<UserLocation>>(emptyList())
    val mapData: State<List<UserLocation>> get() = _mapData

    private val _currentPosition = mutableStateOf<String>("Unknown")
    val currentPosition: State<String> get() = _currentPosition

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)





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
                    updateMapCoordinates(username, location.latitude, location.longitude)
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

    fun updateMapCoordinates(username: String, latitude: Double, longitude: Double ) {
        val userLocation = "Lat: ${latitude}, Long: ${longitude}"
        _currentPosition.value = userLocation
        RetrofitInstance.api.updateMapCoordinates(username, latitude, longitude)
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

    fun updateHardcodedCoordinates(count: Int, username: String) {
        when (count) {
            1 -> updateMapCoordinates(username, 45.7609891613873, 21.216900718790054)
            2 -> updateMapCoordinates(username, 45.76149326939873, 21.214746504800743)
            3 -> updateMapCoordinates(username, 45.76254817915786, 21.211936260867105)
            5 -> updateMapCoordinates(username, 45.760409362137324, 21.209555515299698)
            4 -> updateMapCoordinates(username, 45.763930640828335, 21.206807235278458)
            else -> Log.d("MapViewModel", "No coordinates for count: $count")
        }
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






    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}