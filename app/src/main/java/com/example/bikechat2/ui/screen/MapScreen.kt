package com.example.bikechat2.ui.screen

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bikechat2.data.model.MapViewModel
import com.example.bikechat2.ui.components.BottomNavigationBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    username: String,
    onFriendsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val viewModel: MapViewModel = viewModel()
    val mapData by viewModel.mapData
    val currentPosition by viewModel.currentPosition
    val context = LocalContext.current

    // Fetch the user's location once the screen is launched
    LaunchedEffect(Unit) {
        viewModel.fetchUserLocation(context, username)
    }

    // Google map camera position state
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 10f)
    }

    // Parse the currentPosition to extract latitude and longitude
    val latLng = parseLocation(currentPosition)

    // If the currentPosition is valid, update the camera position
    latLng?.let {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f) // Adjust zoom level here
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onFriendsClick = onFriendsClick,
                onMapClick = { /* No-op */ },
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(text = "Map", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Current Position: $currentPosition", style = MaterialTheme.typography.titleMedium)

            // Google Map view
            GoogleMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier.fillMaxSize()
            ) {
                latLng?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Current Location"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(mapData.size) { index ->
                    val data = mapData[index]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = data, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

// Function to parse the latitude and longitude from the currentPosition string
fun parseLocation(position: String): LatLng? {
    val regex = """Lat:\s*([\d.-]+),\s*Long:\s*([\d.-]+)""".toRegex()
    val matchResult = regex.find(position)

    return if (matchResult != null) {
        val latitude = matchResult.groupValues[1].toDoubleOrNull()
        val longitude = matchResult.groupValues[2].toDoubleOrNull()

        if (latitude != null && longitude != null) {
            LatLng(latitude, longitude)
        } else {
            null
        }
    } else {
        null
    }
}