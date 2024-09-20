package com.example.reciepeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(navHostController: NavHostController) {
    val viewModel : MainViewModel = viewModel()
    val state by viewModel.categoryState

    NavHost(navController = navHostController, startDestination = Screen.RecipeScreen.route ) {
         composable(route = Screen.RecipeScreen.route) {
             RecipeScreen(viewState = state, navigateToDetailsScreen = {
                 navHostController.currentBackStackEntry?.savedStateHandle?.set("category", it)
                 navHostController.navigate(Screen.DetailScreen.route)
             })
         }

        composable(route = Screen.DetailScreen.route) {
            val category =
                navHostController
                    .previousBackStackEntry?.savedStateHandle?.get<Category>("category")
                    ?: emptyCategory()
            DetailsScreen(category)
        }
    }
}

fun emptyCategory(): Category {
    return Category("","empty", "", "")
}
