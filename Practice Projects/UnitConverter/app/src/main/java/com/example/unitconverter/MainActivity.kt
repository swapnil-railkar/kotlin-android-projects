package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }

    var fromUnit by remember { mutableStateOf("Meters") }
    var toUnit by remember { mutableStateOf("Meters") }

    var fromConversionFactor =  remember { mutableDoubleStateOf(1.00) }
    var toConversionFactor =  remember { mutableDoubleStateOf(1.00) }

    var fromDropOpen by remember { mutableStateOf(false) }
    var toDropOpen by remember { mutableStateOf(false) }

    var showOutput by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // All elements will be stacked below each other.
        Text(text = "Unit Converter")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
            },
            label = {Text(text = "Enter Number")}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            // All elements will be stacked next to each other

            Box {
                Button(onClick = {
                    fromDropOpen = true
                }) {
                    Text(text = fromUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Drop down")
                }

                // Responsible for setting fromUnit, fromConversionFactor, fromDropOpen
                DropdownMenu(expanded = fromDropOpen, onDismissRequest = { fromDropOpen = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            fromDropOpen = false
                            fromConversionFactor.doubleValue = 1.00
                            fromUnit = "Meters"
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            fromDropOpen = false
                            fromConversionFactor.doubleValue = 0.01
                            fromUnit = "Centimeters"
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = {
                            fromDropOpen = false
                            fromConversionFactor.doubleValue = 0.001
                            fromUnit = "Millimeters"
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = {
                            fromDropOpen = false
                            fromConversionFactor.doubleValue = 0.3048
                            fromUnit = "Feet"
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Inch") },
                        onClick = {
                            fromDropOpen = false
                            fromConversionFactor.doubleValue = 0.0254
                            fromUnit = "Inch"
                        })
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Box {
                Button(onClick = {
                    toDropOpen = true
                }) {
                    Text(text = toUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Drop down")
                }

                DropdownMenu(expanded = toDropOpen, onDismissRequest = { toDropOpen = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            toDropOpen = false
                            toConversionFactor.doubleValue = 1.00
                            toUnit = "Meters"
                            showOutput = true
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            toDropOpen = false
                            toConversionFactor.doubleValue = 0.01
                            toUnit = "Centimeters"
                            showOutput = true
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = {
                            toDropOpen = false
                            toConversionFactor.doubleValue = 0.001
                            toUnit = "Millimeters"
                            showOutput = true
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = {
                            toDropOpen = false
                            toConversionFactor.doubleValue = 0.3048
                            toUnit = "Feet"
                            showOutput = true
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Inch") },
                        onClick = {
                            toDropOpen = false
                            toConversionFactor.doubleValue = 0.0254
                            toUnit = "Inch"
                            showOutput = true
                        })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (showOutput) {
            outputValue = converter(inputValue,
                fromConversionFactor.doubleValue, toConversionFactor.doubleValue)
            val outputText = "$inputValue $fromUnit is equal to $outputValue $toUnit"
            Text(text = outputText)
        } else {
            Text(text = "Result")
        }

    }
}

fun converter(inputValue : String, fromConversionFactor : Double,
              toConversionFactor : Double): String {
    val inputDouble = inputValue.toDoubleOrNull() ?: 0.0
    val outputDouble = ((inputDouble * fromConversionFactor * 100.00)/toConversionFactor) / 100.00
    return outputDouble.toString()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnitConverter()
}