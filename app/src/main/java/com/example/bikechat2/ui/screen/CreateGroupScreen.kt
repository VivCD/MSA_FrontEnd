package com.example.bikechat2.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bikechat2.data.model.GroupViewModel
import com.example.bikechat2.ui.components.BottomNavigationBar
import androidx.compose.material3.*
import androidx.compose.ui.Modifier

@Composable
fun CreateGroupScreen(
    username: String,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val groupsViewModel: GroupViewModel = viewModel()
    val groupName = remember { mutableStateOf("") }

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
            Text(text = "Manage Groups", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Section to create a group
            Text("Create a New Group", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = groupName.value,
                onValueChange = { groupName.value = it },
                label = { Text("Group Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = groupName.value.isBlank()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (groupName.value.isNotBlank()) {
                        groupsViewModel.createGroup(username, groupName.value)
                        groupName.value = ""

                    } else {
                        println("Group name cannot be empty!")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Group")
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}