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
import com.example.bikechat2.data.model.UserLocation
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

    LaunchedEffect(Unit) {
        viewModel.fetchUserLocation(context, username)
        viewModel.fetchNearbyLocations(username)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 10f)
    }

    val latLng = parseLocation(currentPosition)

    latLng?.let {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 25f)
    }

    var selectedLocation by remember { mutableStateOf<UserLocation?>(null) }
    var showDialog by remember { mutableStateOf(false) } // State to control dialog visibility

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
                modifier = Modifier.fillMaxSize(),
                onMapClick = {
                    // Dismiss any selected location info window when the map is clicked
                    selectedLocation = null
                }
            ) {
                // Add the current location marker
                latLng?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Current Location"
                    )
                }

                // Iterate through the map data (now a list of UserLocation)
                mapData.forEach { locationData ->
                    val markerLatLng = LatLng(
                        locationData.latitude?: 0.0,
                        locationData.longitude?: 0.0)
                    Marker(
                        state = MarkerState(position = markerLatLng),
                        title = "Nearby Location",
                        onClick = {
                            selectedLocation = locationData
                            showDialog = true // Show the dialog when a location is selected
                            true
                        }
                    )
                }
            }

            // Show InfoWindowDialog if a location is selected
            if (showDialog) {
                selectedLocation?.let {
                    InfoWindowDialog(
                        username = it.username ?: "Unknown User",
                        onButton1Click = { /* Handle Button 1 click */ },
                        onButton2Click = { /* Handle Button 2 click */ },
                        onCloseClick = { showDialog = false } // Close the dialog when the button is clicked
                    )
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


@Composable
fun InfoWindowDialog(username: String, onButton1Click: () -> Unit, onButton2Click: () -> Unit, onCloseClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onCloseClick() },
        title = { Text("User: $username") },
        text = {
            Column {
                Button(onClick = onButton1Click) {
                    Text("Button 1")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onButton2Click) {
                    Text("Button 2")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onCloseClick) { // Handle close button click
                Text("Close")
            }
        }
    )
}

