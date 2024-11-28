package com.todoify.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todoify.viewmodel.TaskViewModel
import com.todoify.views.HistoryView
import com.todoify.views.MainView

@Composable
fun AppNavigator(
    navController : NavHostController = rememberNavController(),
    taskViewModel: TaskViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route) {
        composable(Screens.MainScreen.route) {
            MainView(taskViewModel, navController)
        }
        composable(Screens.HistoryScreen.route) { 
            HistoryView(taskViewModel = taskViewModel, navController = navController)
        }
    }
}