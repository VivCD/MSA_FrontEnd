package com.example.bikechat2.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bikechat2.data.api.ApiResponse
import com.example.bikechat2.data.api.RetrofitInstance
import com.example.bikechat2.data.model.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToSignIn: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                errorMessage = null
                coroutineScope.launch {
                    val loginRequest = LoginRequest(username, password)
                    RetrofitInstance.api.loginUser(loginRequest).enqueue(object : Callback<ApiResponse> {
                        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                            if (response.isSuccessful && response.body()?.success == true) {
                                onLoginSuccess()
                            } else {
                                errorMessage = response.body()?.message ?: "Login failed"
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
            Text("Log In")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToSignIn) {
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