package com.example.bikechat2

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bikechat2.data.model.MainViewModel
import com.example.bikechat2.ui.screen.HomeScreen
import com.example.bikechat2.ui.screen.LoginScreen
import com.example.bikechat2.ui.screen.SignInScreen
import com.example.bikechat2.ui.screen.FriendsScreen
import com.example.bikechat2.ui.screen.MapScreen
import com.example.bikechat2.ui.screen.ProfileScreen

@Composable
fun MainScreen(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val navController = rememberNavController()

    // Observe the current username from ViewModel
    val currentUser = viewModel.currentUsername

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { username ->
                    viewModel.setCurrentUsername(username) // Update ViewModel with the logged-in username
                    navController.navigate("home")
                },
                onNavigateToSignIn = { navController.navigate("signIn") }
            )
        }
        composable("signIn") {
            SignInScreen(onSignInComplete = { username ->
                viewModel.setCurrentUsername(username) // Update ViewModel
                navController.navigate("home")
            })
        }
        composable("home") {
            HomeScreen(
                onRideModeClick = { /* Handle Ride Mode */ },
                onInitializeCallClick = { /* Handle Initialize Call */ },
                onMusicClick = { /* Handle Music */ },
                onFriendsClick = {
                    if (currentUser.isNotEmpty()) {
                        navController.navigate("friends/$currentUser")
                    }
                },
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
