package com.example.chatroom.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatroom.viewModel.AuthViewModel
import com.example.chatroom.views.ChatRoomScreen
import com.example.chatroom.views.LoginScreen
import com.example.chatroom.views.SignupScreen

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = Screens.SignUpScreen.route) {
        composable(Screens.SignUpScreen.route) {
            SignupScreen(authViewModel) { navController.navigate(Screens.LoginScreen.route) }
        }
        composable(Screens.LoginScreen.route) {
            LoginScreen(authViewModel,
                onNavigateToSignUpScreen = {navController.navigate(Screens.SignUpScreen.route)}) {
                // log in functionality
                navController.navigate(Screens.ChatRoomsScreen.route)
            }
        }
        composable(Screens.ChatRoomsScreen.route) {
            ChatRoomScreen()
        }
    }
}