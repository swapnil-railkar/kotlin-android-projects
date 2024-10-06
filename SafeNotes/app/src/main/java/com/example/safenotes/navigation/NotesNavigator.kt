package com.example.safenotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.safenotes.views.AddEditScreen
import com.example.safenotes.views.HomePage

@Composable
fun NotesNavigator(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(navController = navHostController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(Screens.HomeScreen.route) {
            HomePage(navController = navHostController)
        }

        composable(
            Screens.AddEditScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    nullable = false
                    defaultValue = 0L
                }
            )
        ) {
            entry ->
            val id = entry.arguments!!.getLong("id")
            AddEditScreen(id = id)
        }
    }
}