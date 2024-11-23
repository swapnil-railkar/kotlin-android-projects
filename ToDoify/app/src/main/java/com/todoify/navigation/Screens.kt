package com.todoify.navigation

sealed class Screens (public val route: String) {
    data object MainScreen : Screens("main_screen")
}