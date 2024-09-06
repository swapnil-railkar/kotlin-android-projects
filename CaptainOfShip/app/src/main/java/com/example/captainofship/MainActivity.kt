package com.example.captainofship

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.captainofship.ui.theme.CaptainOfShipTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaptainOfShipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CaptainOfShip()
                }
            }
        }
    }
}

@Composable
fun CaptainOfShip() {
    val count = remember {
        mutableStateOf(0)
    }

    val direction = remember {
        mutableStateOf("North")
    }

    val stormOrTreasure = remember {
        mutableStateOf("")
    }
    

    
    Column {
        Text(text = "Treasures found : ${count.value}")
        Text(text = "Current direction : ${direction.value}")
        Text(text = "You found : ${stormOrTreasure.value}")
        Button(onClick = {
            direction.value = "North"
            if (Random.nextBoolean()) {
                stormOrTreasure.value = "Treasure"
                count.value++
            } else {
                stormOrTreasure.value = "Storm"
            }
        }) {
            Text(text = "Go North")
        }

        Button(onClick = {
            direction.value = "South"
            if (Random.nextBoolean()) {
                stormOrTreasure.value = "Treasure"
                count.value++
            } else {
                stormOrTreasure.value = "Storm"
            }
        }) {
            Text(text = "Go South")
        }

        Button(onClick = {
            direction.value = "East"
            if (Random.nextBoolean()) {
                stormOrTreasure.value = "Treasure"
                count.value++
            } else {
                stormOrTreasure.value = "Storm"
            }
        }) {
            Text(text = "Go East")
        }

        Button(onClick = {
            if (Random.nextBoolean()) {
                stormOrTreasure.value = "Treasure"
                count.value++
            } else {
                stormOrTreasure.value = "Storm"
            }
        }) {
            Text(text = "Go West")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CaptainOfShipTheme {
        CaptainOfShip()
    }
}