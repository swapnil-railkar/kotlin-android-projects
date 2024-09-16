package com.example.navigationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationdemo.ui.theme.NavigationDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavigator()
                }
            }
        }
    }
}

@Composable
fun RootNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "first_screen") {
        composable(route = "first_screen") {
            FirstScreen {
                 age, name ->navController.navigate("second_screen/{$age}/{$name}")
            }
        }
        composable(route = "second_screen/{age}/{name}") {
            val name = it.arguments?.getString("name") ?: ""
            val age = it.arguments?.getInt("age") ?: -1
            SecondScreen(age, name) {
                navController.navigate("third_screen")
            }
        }
        composable(route = "third_screen") {
            ThirdScreen {
                navController.navigate("first_screen")
            }
        }
    }
}