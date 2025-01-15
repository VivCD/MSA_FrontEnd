package com.example.bikechat2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bikechat2.data.model.FriendsViewModel
import com.example.bikechat2.ui.components.BottomNavigationBar

@Composable
fun FriendsScreen(
    username: String,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val viewModel: FriendsViewModel = viewModel()
    val friendsList by viewModel.friendsList
    val friendRequests by viewModel.friendRequests
    val newFriendUsername = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchFriends(username)
        viewModel.fetchFriendRequests(username)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(text = "Friends", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Your Friends", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(friendsList.size) { index ->
                    val friend = friendsList[index]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = friend, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Friend Requests", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(friendRequests.size) { index ->
                    val request = friendRequests[index]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = request, style = MaterialTheme.typography.bodyLarge)
                        println("DEBUG: Logged-in username: $username")
                        println("DEBUG: Recipient username: ${newFriendUsername.value}")
                        Button(onClick = { viewModel.acceptFriendRequest(request, username) }) {
                            Text("Accept")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = newFriendUsername.value,
                    onValueChange = { newFriendUsername.value = it },
                    label = { Text("Enter Username") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                println("DEBUG: Logged-in username: $username")
                println("DEBUG: Recipient username: ${newFriendUsername.value}")
                Button(onClick = {
                    viewModel.sendFriendRequest(username, newFriendUsername.value)
                }) {
                    Text("Send Request")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Groups", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Your Groups", style = MaterialTheme.typography.titleMedium)
        }
    }
}
