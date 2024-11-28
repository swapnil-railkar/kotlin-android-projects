package com.example.safenotes.navigation

sealed class Screens(val route : String) {
    data object HomeScreen : Screens("home_screen")
    data object AddEditScreen : Screens("add_edit_screen")
}