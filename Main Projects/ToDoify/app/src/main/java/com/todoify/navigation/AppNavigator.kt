package com.todoify.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todoify.viewmodel.TaskAgeLimitViewModel
import com.todoify.viewmodel.TaskViewModel
import com.todoify.views.HistoryView
import com.todoify.views.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Composable
fun AppNavigator(
    navController: NavHostController = rememberNavController(),
    taskViewModel: TaskViewModel = viewModel(),
    taskAgeLimitViewModel: TaskAgeLimitViewModel = viewModel()
) {
    val rows = taskAgeLimitViewModel.getRows().collectAsState(initial = -1)
    initializeTaskAgeLimit(taskAgeLimitViewModel, rows.value)

    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route
    ) {
        composable(Screens.MainScreen.route) {
            MainView(taskViewModel, navController, taskAgeLimitViewModel)
        }
        composable(Screens.HistoryScreen.route) {
            HistoryView(taskViewModel, navController, taskAgeLimitViewModel)
        }
    }
}

fun initializeTaskAgeLimit(taskAgeLimitViewModel: TaskAgeLimitViewModel, rows: Int) {
    val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    scope.launch {
        taskAgeLimitViewModel.initializeTaskAgeLimit(rows)
    }
}