package com.example.musicappui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicappui.MainViewModel
import com.example.musicappui.views.AccountView
import com.example.musicappui.views.Browse
import com.example.musicappui.views.Home
import com.example.musicappui.views.Library

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screens.BottomScreen.Home.bottomRoute,
        modifier = Modifier.padding(paddingValues)) {

        composable(Screens.BottomScreen.Home.bottomRoute) {
            Home()
        }

        composable(Screens.BottomScreen.Browse.bottomRoute) {
            Browse()
        }

        composable(Screens.BottomScreen.Library.bottomRoute) {
            Library()
        }

        composable(Screens.DrawerScreen.Account.route) {
            AccountView()
        }

        composable(Screens.DrawerScreen.Subscription.route) {

        }
    }
}