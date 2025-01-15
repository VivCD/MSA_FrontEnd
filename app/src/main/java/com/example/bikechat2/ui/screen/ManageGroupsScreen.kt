package com.example.bikechat2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bikechat2.data.model.GroupViewModel
import com.example.bikechat2.ui.components.BottomNavigationBar
import androidx.compose.runtime.getValue

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue


@Composable
fun ManageGroupsScreen(
    username: String,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val groupViewModel: GroupViewModel = viewModel()

    val groupList by groupViewModel.groupList

    val groupIdToJoin = remember { mutableStateOf("") }

    LaunchedEffect(username) {
        println("Fetching groups for username: $username")  // Debug
        groupViewModel.fetchGroups(username)
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
            Text(text = "Groups", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Your Groups", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(groupList) { group ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Group name in white color
                        Text(
                            text = group.groupName.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                        Button(onClick = {
                            groupViewModel.leaveGroup(username, group.groupId.toString())
                        }) {
                            Text("Leave Group")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Spacer between list and text field
            Spacer(modifier = Modifier.height(16.dp))

            // TextField for entering groupId to join
            OutlinedTextField(
                value = groupIdToJoin.value,
                onValueChange = { groupIdToJoin.value = it },
                label = { Text("Enter Group ID to Join") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Join Group button
            Button(
                onClick = {
                    if (groupIdToJoin.value.isNotBlank()) {
                        groupViewModel.joinGroup(username, groupIdToJoin.value)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Join Group")
            }
        }
    }
}
