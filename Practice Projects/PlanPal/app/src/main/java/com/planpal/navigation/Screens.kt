package com.planpal.navigation

sealed class Screens(val route: String) {
    data object TaskScreen: Screens("task_screen")
}