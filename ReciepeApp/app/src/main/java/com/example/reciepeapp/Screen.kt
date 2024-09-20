package com.example.reciepeapp

sealed class Screen(val route : String){
    data object RecipeScreen : Screen("recipe_screen")
    data object DetailScreen : Screen("details_screen")
}