package com.example.bikechat2.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.bikechat2.data.model.Discoverability
import com.example.bikechat2.data.model.ProfileViewModel
import com.example.bikechat2.ui.components.BottomNavigationBar

@Composable
fun ProfileScreen(
    username: String,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit,
    navController: NavController
) {
    val profileViewModel: ProfileViewModel = viewModel()

    val user = profileViewModel.user.value

    val showDialog = remember { mutableStateOf(false) }
    val editingField = remember { mutableStateOf("") }
    val newValue = remember { mutableStateOf("") }
    var selectedOption = remember { mutableStateOf(Discoverability.EVERYONE) }


    LaunchedEffect(username) {
        profileViewModel.getUserData(username)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onFriendsClick = { /* No-op */ },
                onMapClick = onMapClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Hello, ${user.username}",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 26.sp),
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(top = 30.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                editingField.value = "url"
                                newValue.value = user.profilePictureUrl ?: ""
                                showDialog.value = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (user.profilePictureUrl.isNullOrEmpty()) {
                            // Placeholder circle
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Change Picture",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.DarkGray
                                )
                            }
                        } else {
                            Image(
                                painter = rememberImagePainter(user.profilePictureUrl),
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(180.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Email: ${user.email}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f)) // This will push the button to the right

                Button(onClick = {
                    editingField.value = "email"
                    newValue.value = user.email ?: ""
                    showDialog.value = true
                }) {
                    Text("Change Email")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bio: ${user.bio ?: "No bio available"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f)) // This will push the button to the right

                Button(
                    onClick = {
                        editingField.value = "bio"
                        newValue.value = user.bio ?: ""
                        showDialog.value = true

                    },

                    ) {
                    Text("Change Bio")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Discoverability: ${user.locationDiscoverability}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f)) // This will push the button to the right

                Button(
                    onClick = {
                        editingField.value = "locationDiscoverability"
                        showDialog.value = true
                    },
                ) {
                    Text("Change visibility")
                }
            }
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text("Change ${editingField.value.replaceFirstChar { it.uppercase() }}") },
                text = {
                    if (editingField.value == "locationDiscoverability") {

                        Column {
                            Text("Select Location Discoverability:")
                            Discoverability.values().forEach { option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = selectedOption.value == option,
                                        onClick = { selectedOption.value = option }
                                    )
                                    Text(
                                        text = option.name.replaceFirstChar { it.uppercase() },
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        Column {
                            Text("Enter your new ${editingField.value}:")
                            TextField(
                                value = newValue.value,
                                onValueChange = { newValue.value = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text("New ${editingField.value.replaceFirstChar { it.uppercase() }}")
                                }
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (editingField.value == "locationDiscoverability") {
                                profileViewModel.updateLocationDiscoverability(
                                    username = username,
                                    discoverability = selectedOption.value
                                )
                            } else {
                                when (editingField.value) {
                                    "email" -> profileViewModel.updateEmail(
                                        username,
                                        newValue.value
                                    )

                                    "bio" -> profileViewModel.updateBio(
                                        username,
                                        newValue.value
                                    )
                                    "url" -> profileViewModel.updateProfilePicture(
                                        username,
                                        newValue.value
                                    )
                                }
                            }
                            showDialog.value = false
                        }
                    ) {
                        Text("Send")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog.value = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}


