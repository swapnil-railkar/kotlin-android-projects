package com.example.musicappui.navigation

import androidx.annotation.DrawableRes
import com.example.musicappui.R

sealed class Screens(val title: String, val route: String) {
    sealed class DrawerScreen(
        private val drawerTitle: String,
        private val drawerRoute: String,
        @DrawableRes val icon: Int
    ) : Screens(drawerTitle, drawerRoute) {
        data object Account: DrawerScreen (
            drawerTitle = "Account",
            drawerRoute = "account",
            icon = R.drawable.baseline_person_24
        )

        data object Subscription: DrawerScreen(
            drawerTitle = "Subscriptions",
            drawerRoute = "subcriptions",
            icon = R.drawable.baseline_subscriptions_24
        )

        data object AddAccount: DrawerScreen(
            drawerTitle = "Add Account",
            drawerRoute = "add_account",
            icon = R.drawable.baseline_person_add_alt_1_24
        )
    }

    sealed class BottomScreen(
        val bottomTitle: String,
        val bottomRoute: String,
        @DrawableRes val icon: Int
    ){
        data object Home: BottomScreen(
            bottomTitle = "Home",
            bottomRoute = "home",
            icon = R.drawable.baseline_home_24
        )

        data object Library: BottomScreen(
            bottomTitle = "Library",
            bottomRoute = "library",
            icon = R.drawable.baseline_library_music_24
        )

        data object Browse: BottomScreen(
            bottomTitle = "Browse",
            bottomRoute = "browse",
            icon = R.drawable.baseline_album_24
        )
    }
}