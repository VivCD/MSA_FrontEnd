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
            selected = false, // You can use logic to track selection
            onClick = onFriendsClick,
            icon = { Text(text = "Friends") },
            label = { Text("Friends") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = onMapClick,
            icon = { Text(text = "Map") },
            label = { Text("Map") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = onProfileClick,
            icon = { Text(text = "Profile") },
            label = { Text("Profile") }
        )
    }
}



data class BottomNavItem(
    val route: String,
    val label: String
)