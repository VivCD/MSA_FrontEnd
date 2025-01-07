package com.example.bikechat2.ui.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun CallScreen(
    onSendMessage: (String) -> Unit,
    messages: List<String>
) {
    val message = remember { mutableStateOf("") }

    Column {
        LazyColumn {
            items(messages) { msg ->
                Text(msg)
            }
        }

        TextField(
            value = message.value,
            onValueChange = { message.value = it },
            label = { Text("Enter your message") }
        )

        Button(onClick = {
            onSendMessage(message.value)
            message.value = ""
        }) {
            Text("Send")
        }
    }
}
