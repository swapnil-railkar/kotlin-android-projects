package com.todoify.bottombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.todoify.R
import com.todoify.navigation.Screens

@Composable
fun DefaultBottomBar(
    screenContext: String,
    navController: NavController
) {
    BottomAppBar(
        backgroundColor = colorResource(id = R.color.app_default_color)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.navigate(Screens.MainScreen.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24),
                    contentDescription = "Main Screen",
                    tint = getColorForScreen(screenContext, Screens.MainScreen.route)
                )
            }

            IconButton(onClick = { navController.navigate(Screens.HistoryScreen.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_history_24),
                    contentDescription = "History Screen",
                    tint = getColorForScreen(screenContext, Screens.HistoryScreen.route)
                )
            }
        }

    }
}

private fun getColorForScreen(screenContext: String, currScreen: String): Color {
    return if (screenContext == currScreen) Color.White
    else Color.Black
}

@Composable
@Preview(showBackground = true)
fun DefaultBottomBarPreview() {
    val navController = rememberNavController()
    DefaultBottomBar(screenContext = Screens.MainScreen.route, navController = navController)
}