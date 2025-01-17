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
import com.example.bikechat2.ui.screen.GroupSelectScreen
import com.example.bikechat2.ui.screen.CallScreen
import com.example.bikechat2.ui.screen.CreateGroupScreen
import com.example.bikechat2.ui.screen.ManageGroupsScreen

@Composable
fun MainScreen(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val navController = rememberNavController()

    // Observe the current username from ViewModel
    val currentUser = viewModel.currentUsername

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { username ->
                    viewModel.setCurrentUsername(username)
                    navController.navigate("home")
                },
                onNavigateToSignIn = { navController.navigate("signIn") }
            )
        }
        composable("signIn") {
            SignInScreen(onSignInComplete = { username ->
                viewModel.setCurrentUsername(username)
                navController.navigate("home")
            })
        }
        composable("home") {
            HomeScreen(
                onRideModeClick = { /* Handle Ride Mode */ },
                onInitializeCallClick = {
                    navController.navigate("groupSelect")
                },
                onMusicClick = { /* Handle Music */ },
                onFriendsClick = {
                    if (currentUser.isNotEmpty()) {
                        navController.navigate("friends/$currentUser")
                    }
                },
                onMapClick = { navController.navigate("map/$currentUser") },
                onProfileClick = { navController.navigate("profile/$currentUser") }
            )
        }

        composable(
            route = "callScreen/{callLogID}",
            arguments = listOf(navArgument("callLogID") { type = NavType.StringType })
        ) { backStackEntry ->
            val callLogID = backStackEntry.arguments?.getString("callLogID") ?: ""
            CallScreen(
                callLogID = callLogID,
                username = currentUser,
                onEndCall = { navController.popBackStack("home", inclusive = false) }
            )
        }

        composable("groupSelect") {
            GroupSelectScreen(
                username = currentUser,
                navController = navController,  // ✅ Pass the navController here
                onStartCallClick = { callLogID ->
                    navController.navigate("callScreen/$callLogID")
                },
                onFriendsClick = { navController.navigate("friends/$currentUser") },
                onMapClick = { navController.navigate("map/$currentUser") },
                onProfileClick = { navController.navigate("profile/$currentUser") }
            )
        }

        composable(
            route = "friends/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            FriendsScreen(
                onMapClick = { navController.navigate("map/$username") },
                onProfileClick = { navController.navigate("profile/$username") },
                username = username,
                navController = navController
            )
        }
        composable(
            route = "map/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            MapScreen(
                username = username,
                onFriendsClick = { navController.navigate("friends/$username") },
                onProfileClick = { navController.navigate("profile/$username") }
            )
        }
//        composable("profile") {
//            ProfileScreen()
//        }

        composable(
            route = "profile/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            ProfileScreen(
                username = username,
                onMapClick = { navController.navigate("map/$username") },
                onProfileClick = { navController.navigate("profile/$username") },
                navController = navController
            )
        }

        composable(
            route = "manageGroups/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            ManageGroupsScreen(
                username = username,
                onMapClick = { navController.navigate("map/$username") },
                onProfileClick = { navController.navigate("profile/$username") },
                navController = navController
            )
        }

        composable(
            route = "createGroups/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            CreateGroupScreen(
                username = username,
                onMapClick = { navController.navigate("map/$username") },
                onProfileClick = { navController.navigate("profile/$username") }
            )
        }


    }
}

