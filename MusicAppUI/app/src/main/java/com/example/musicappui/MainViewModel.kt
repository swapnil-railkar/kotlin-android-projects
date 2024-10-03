package com.example.musicappui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.musicappui.navigation.Screens

class MainViewModel: ViewModel() {
    private var _currentScreen : MutableState<Screens.DrawerScreen> = mutableStateOf(Screens.DrawerScreen.AddAccount)
    var currentScreen : MutableState<Screens.DrawerScreen> = _currentScreen


    fun setCurrentScreen(drawerScreen : Screens.DrawerScreen) {
        _currentScreen.value = drawerScreen
    }

}