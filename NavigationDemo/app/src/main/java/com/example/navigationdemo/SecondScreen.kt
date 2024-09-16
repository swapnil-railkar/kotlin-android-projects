package com.example.navigationdemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SecondScreen(
    age : Int ,
    name: String,
    navigateToNextScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Second screen", fontSize = 24.sp)

        if (name.isNotEmpty()) {
            Text(text = "Hello $name", fontSize = 24.sp)
        }

        if (age != -1) {
            Text(text = "Age is $age", fontSize = 24.sp, modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navigateToNextScreen() }
        ) {
            Text(text = "Third Page")
        }
    }
}
