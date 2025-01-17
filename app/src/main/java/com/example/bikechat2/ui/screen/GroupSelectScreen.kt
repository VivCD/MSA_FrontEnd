package com.example.bikechat2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bikechat2.data.model.Group
import com.example.bikechat2.ui.components.BottomNavigationBar
import com.example.bikechat2.data.model.GroupViewModel

@Composable
fun GroupSelectScreen(
    username: String,
    navController: NavController,
    onStartCallClick: (String) -> Unit,
    onFriendsClick: () -> Unit,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val viewModel: GroupViewModel = viewModel()
    val groupNames by viewModel.groupNames

    LaunchedEffect(Unit) {
        println("Fetching groups for username: $username")  // Debug
        viewModel.fetchGroups(username)
    }


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
                .padding(16.dp)
        ) {
            Text(text = "Select a Group to Start a Call", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(groupNames) { group ->  // ✅ Changed from groupList.size to groupList
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = group, style = MaterialTheme.typography.bodyLarge)
                        Button(onClick = {
                            viewModel.initiateCall(group) { callLogID ->
                                navController.navigate("callScreen/$callLogID")  // ✅ Direct navigation
                            }
                        }) {
                            Text("Start Call")
                        }
                    }
                }
            }
        }
    }
}
//wwow2