package com.example.bikechat2.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import com.example.bikechat2.ui.components.BottomNavigationBar



@Composable
fun HomeScreen(
    onRideModeClick: () -> Unit,
    onInitializeCallClick: () -> Unit,
    onMusicClick: () -> Unit,
    onFriendsClick: () -> Unit,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onFriendsClick = onFriendsClick,
                onMapClick = onMapClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Ride Mode Button
            Button(
                onClick = onRideModeClick,
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text(text = "Ride Mode Button")
            }

            // Spacer for spacing
            Spacer(modifier = Modifier.weight(1f))

            // Initialize a Call Button
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Initialize a Call",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Spacer for spacing
            Spacer(modifier = Modifier.weight(1f))

            // Music Button
            Button(
                onClick = onMusicClick,
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text(text = "Music")
            }
        }
    }
}