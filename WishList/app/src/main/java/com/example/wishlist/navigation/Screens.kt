package com.example.wishlist.navigation

sealed class Screens(val route : String) {
    data object HomeScreen : Screens("home_screen")
    data object AddScreen : Screens("add_screen")
}