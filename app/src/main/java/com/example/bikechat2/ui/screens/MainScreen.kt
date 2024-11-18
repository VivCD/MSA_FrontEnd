package com.example.bikechat2.ui.screens

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
            HomeScreen(navController, onRideMode = { /* Ride Mode Action */ }, onCallInit = { /* Call Init Action */ })
        }
        composable("friends") {
            FriendsScreen(
                navToMap = { navController.navigate("map") },
                navToProfile = { navController.navigate("profile") }
            )
        }
        composable("map") {
            MapScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
    }
}

