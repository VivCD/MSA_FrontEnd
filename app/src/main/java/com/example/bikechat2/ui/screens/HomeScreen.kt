package com.example.bikechat2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, onRideMode: () -> Unit, onCallInit: () -> Unit) {
    var musicPlaying by remember { mutableStateOf(false) } // Example state for music
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ride Mode Button
        Button(
            onClick = {
                coroutineScope.launch {
                    onRideMode()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Ride Mode Button", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Initialize a Call Button
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        onCallInit()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Initialize\nA Call",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Music Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Music", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (musicPlaying) "Now Playing: Song Title" else "No music playing",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        // Bottom Navigation Bar
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("friends") }) {
                Text(text = "Friends")
            }
            Button(onClick = { navController.navigate("map") }) {
                Text(text = "Map")
            }
            Button(onClick = { navController.navigate("profile") }) {
                Text(text = "Profile")
            }
        }
    }
}