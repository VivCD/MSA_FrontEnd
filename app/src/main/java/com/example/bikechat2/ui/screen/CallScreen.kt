package com.example.bikechat2.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bikechat2.data.model.CallViewModel
@Composable
fun CallScreen(
    callLogID: String,
    username: String,
    callViewModel: CallViewModel = CallViewModel(),
    onEndCall: () -> Unit
) {
    val messages = remember { mutableStateListOf<String>() }
    val messageInput = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        callViewModel.connectToRoom(callLogID, username) { message ->
            messages.add(message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Group Call") })
        },
        bottomBar = {
            Button(
                onClick = {
                    callViewModel.disconnect()
                    onEndCall()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("End Call")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(messages) { msg ->
                    Text(text = msg)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                TextField(
                    value = messageInput.value,
                    onValueChange = { messageInput.value = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message") }
                )

                Button(onClick = {
                    if (messageInput.value.isNotEmpty()) {
                        callViewModel.sendMessage(messageInput.value)
                        messageInput.value = ""
                    }
                }) {
                    Text("Send")
                }
            }
        }
    }
}
