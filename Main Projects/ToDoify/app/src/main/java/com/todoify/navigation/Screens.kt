package com.todoify.navigation

sealed class Screens(val route: String) {
    data object MainScreen : Screens("main_screen")
    data object HistoryScreen : Screens("history_screen")
}