package com.example.musicappui.views

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.musicappui.navigation.Screens

@Composable
fun BottomAppBarUI(
    currentRoute: String,
    navController: NavController
){
    val screensInBottom = listOf<Screens.BottomScreen>(
        Screens.BottomScreen.Home,
        Screens.BottomScreen.Library,
        Screens.BottomScreen.Browse
    )

    
    BottomNavigation(modifier = Modifier.wrapContentSize()) {
        screensInBottom.listIterator().forEach { 
            screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.bottomRoute,
                onClick = { navController.navigate(screen.bottomRoute) },
                icon = { 
                    Icon(painter = painterResource(id = screen.icon), 
                        contentDescription = screen.bottomTitle)
                },
                label = { Text(text = screen.bottomTitle)}
            )
        }
    }
}
