package com.example.bikechat2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FriendsScreen(navToMap: () -> Unit, navToProfile: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title Section
        Text(
            text = "LIST OF FRIENDS/PENDING REQUESTS/CONTACTS",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Friends List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) { // Example with 5 items
                FriendItem(name = "Friend ${it + 1}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // People Nearby Button
        Button(
            onClick = { /* TODO: Add action for People Nearby */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "People Near By", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Already on Friends page */ }) {
                Text(text = "Friends")
            }
            Button(onClick = navToMap) {
                Text(text = "Map")
            }
            Button(onClick = navToProfile) {
                Text(text = "Profile")
            }
        }
    }
}

@Composable
fun FriendItem(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder for Friend's Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))

        // Friend's Name
        Text(text = name, fontSize = 16.sp)
    }
}