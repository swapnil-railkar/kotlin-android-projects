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

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screens.DrawerScreen.Account.route,
        modifier = Modifier.padding(paddingValues)) {
        composable(Screens.DrawerScreen.Account.route) {
            AccountView()
        }
        composable(Screens.DrawerScreen.Subscription.route) {

        }
    }
}