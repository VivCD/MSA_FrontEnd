package com.example.bikechat2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bikechat2.ui.screen.HomeScreen
import com.example.bikechat2.ui.screen.LoginScreen
import com.example.bikechat2.ui.screen.SignInScreen
import com.example.bikechat2.ui.screen.FriendsScreen
import com.example.bikechat2.ui.screen.MapScreen
import com.example.bikechat2.ui.screen.ProfileScreen

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
            HomeScreen(
                onRideModeClick = { /* Handle Ride Mode */ },
                onInitializeCallClick = { /* Handle Initialize Call */ },
                onMusicClick = { /* Handle Music */ },
                onFriendsClick = { navController.navigate("friends") },
                onMapClick = { navController.navigate("map") },
                onProfileClick = { navController.navigate("profile")}
            )
        }
        composable("friends") { FriendsScreen() }
        composable("map") { MapScreen() }
        composable("profile") { ProfileScreen() }
    }
}
