package com.example.bikechat2

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bikechat2.ui.screen.CallScreen
import com.example.bikechat2.ui.screen.HomeScreen
import com.example.bikechat2.ui.screen.LoginScreen
import com.example.bikechat2.ui.screen.SignInScreen
import com.example.bikechat2.ui.screen.FriendsScreen
import com.example.bikechat2.ui.screen.MapScreen
import com.example.bikechat2.ui.screen.ProfileScreen
import com.example.bikechat2.ui.screen.GroupSelectScreen

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
                onInitializeCallClick = {
                    navController.navigate("groupSelect")
                },
                onMusicClick = { /* Handle Music */ },
                onFriendsClick = {
                    navController.navigate("friends/{username}".replace("{username}", "currentUser"))
                },
                onMapClick = { navController.navigate("map") },
                onProfileClick = { navController.navigate("profile") }
            )
        }

        composable("groupSelect") {
            GroupSelectScreen(
                username = currentUser,
                onStartCallClick = { groupName ->
                    // Handle starting a call here
                },
                onFriendsClick = { navController.navigate("friends/$currentUser") },
                onMapClick = { navController.navigate("map") },
                onProfileClick = { navController.navigate("profile") }
            )
        }

        composable(
            route = "friends/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            FriendsScreen(
                onMapClick = { navController.navigate("map") },
                onProfileClick = { navController.navigate("profile") },
                username = username
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
