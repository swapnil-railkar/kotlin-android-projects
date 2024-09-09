package com.example.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Calculator() {

    val equation by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = equation,
            color = Color.Black,
            fontSize = 40.sp,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(end = 10.dp),
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorButton(text = "C", onClick = {  }, isSymbol = true)
            CalculatorButton(text = "()", onClick = { /*TODO*/ }, isSymbol = true)
            CalculatorButton(text = "%", onClick = { /*TODO*/ }, isSymbol = true)
            CalculatorButton(text = "/", onClick = { /*TODO*/ }, isSymbol = true)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorButton(text = "7", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "8", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "9", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "X", onClick = { /*TODO*/ }, isSymbol = true)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorButton(text = "4", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "5", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "6", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "-", onClick = { /*TODO*/ }, isSymbol = true)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorButton(text = "1", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "2", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "3", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "+", onClick = { /*TODO*/ }, isSymbol = true)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorButton(text = " ", onClick = {}, isSymbol = false)
            CalculatorButton(text = "0", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = ".", onClick = { /*TODO*/ }, isSymbol = false)
            CalculatorButton(text = "=", onClick = { /*TODO*/ }, isSymbol = true)
        }

    }

}

@Composable
fun CalculatorButton(
    text : String,
    onClick : () -> Unit,
    isSymbol : Boolean
) {

    if (isSymbol) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
    } else {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = text,
                color = Color.Black,
                fontSize = 20.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    Calculator()
}