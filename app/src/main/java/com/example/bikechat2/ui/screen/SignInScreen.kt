package com.example.bikechat2.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme
import com.example.bikechat2.data.api.ApiResponse
import com.example.bikechat2.data.api.RetrofitInstance
import com.example.bikechat2.data.model.User

@Composable
fun SignInScreen(onSignInComplete: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profilePictureUrl by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = bio, onValueChange = { bio = it }, label = { Text("Bio") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = profilePictureUrl, onValueChange = { profilePictureUrl = it }, label = { Text("Profile Picture URL") })
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                errorMessage = null
                coroutineScope.launch {
                    val user = User(
                        userID = null,
                        username = username,
                        email = email,
                        password = password,
                        bio = bio,
                        profilePictureUrl = profilePictureUrl
                    )
                    RetrofitInstance.api.registerUser(user).enqueue(object : Callback<ApiResponse> {
                        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                            if (response.isSuccessful && response.body()?.success == true) {
                                onSignInComplete()
                            } else {
                                errorMessage = response.body()?.message ?: "Registration failed"
                            }
                        }

                        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                            errorMessage = "Network error: ${t.message}"
                        }
                    })
                }
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text("Sign In")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}