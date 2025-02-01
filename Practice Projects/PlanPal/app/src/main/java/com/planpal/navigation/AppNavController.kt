package com.planpal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.planpal.views.TaskScreenView

@Composable
fun AppNavController(navController: NavHostController = rememberNavController()) {

    NavHost(navController = navController, startDestination = Screens.TaskScreen.route) {
        composable(Screens.TaskScreen.route) {
            TaskScreenView()
        }
    }
}