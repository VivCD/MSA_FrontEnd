package com.example.bikechat2.ui.components

import androidx.compose.runtime.Composable
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text

@Composable
fun BottomNavigationBar(
    onFriendsClick: () -> Unit,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    BottomNavigation {
        BottomNavigationItem(
            selected = false, // Update with logic to track selection if needed
            onClick = onFriendsClick,
            icon = { /* Optionally add an icon here */ },
            label = { Text("Friends") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = onMapClick,
            icon = { /* Optionally add an icon here */ },
            label = { Text("Map") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = onProfileClick,
            icon = { /* Optionally add an icon here */ },
            label = { Text("Profile") }
        )
    }
}




data class BottomNavItem(
    val route: String,
    val label: String
)