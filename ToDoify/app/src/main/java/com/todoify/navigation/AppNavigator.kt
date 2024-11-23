package com.todoify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todoify.views.MainView

@Composable
fun AppNavigator(
    navController : NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route) {
        composable(Screens.MainScreen.route) {
            MainView()
        }
    }
}