package com.example.bikechat2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") },
                onNavigateToSignIn = { navController.navigate("signIn") }
            )
        }
        composable("signIn") {
            SignInScreen(onSignInComplete = { navController.navigate("home") })
        }
        composable("home") {
            HomeScreen()
        }
    }
}
