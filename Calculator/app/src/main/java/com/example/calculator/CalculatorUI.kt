package com.example.calculator

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Calculator() {

    val calculatorViewModel : CalculatorViewModel = viewModel()

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = calculatorViewModel.equation.value,
            color = Color.Black,
            fontSize = 40.sp,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(end = 10.dp),
        )

        IconButton(onClick = {
            calculatorViewModel.backSpace()
        }) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Remove last character")
        }


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val values = mutableListOf<String>("C","()","%","/")
            ButtonRow(values = values, calculatorViewModel = calculatorViewModel)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val values = mutableListOf<String>("7","8","9","x")
            ButtonRow(values = values, calculatorViewModel = calculatorViewModel)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val values = mutableListOf<String>("4","5","6","-")
            ButtonRow(values = values, calculatorViewModel = calculatorViewModel)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val values = mutableListOf<String>("1","2","3","+")
            ButtonRow(values = values, calculatorViewModel = calculatorViewModel)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalculatorButton(text = " ", onClick = {}, isSymbol = false)
            val values = mutableListOf<String>("0",".","=")
            ButtonRow(values = values, calculatorViewModel = calculatorViewModel)
        }

    }

}

@Composable
fun ButtonRow(
    values : List<String>,
    calculatorViewModel: CalculatorViewModel,
) {
    val context = LocalContext.current
    for (value in values) {
        val num = value.toDoubleOrNull()
        CalculatorButton(
            text = value,
            onClick = {
                updateEquation(value, calculatorViewModel, context)
            },
            isSymbol = num == null
        )
    }
}

fun updateEquation(
    value : String,
    calculatorViewModel : CalculatorViewModel,
    context : Context
) {
    when {
        value == "C" -> calculatorViewModel.clearEquation()
        value == "()" -> calculatorViewModel.addBrackets()
        value == "=" -> calculatorViewModel.getAnswer()
        calculatorViewModel.canAdd(value) -> calculatorViewModel.updateEquation(value)
        else -> {
            Toast
                .makeText(context, "Invalid Input", Toast.LENGTH_LONG)
                .show()
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