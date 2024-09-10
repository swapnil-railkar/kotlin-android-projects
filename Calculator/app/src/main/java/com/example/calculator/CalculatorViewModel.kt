package com.example.calculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel(){
    private val _equation = mutableStateOf("")

    val equation : State<String> = _equation

    fun clearEquation() {
        _equation.value = ""
    }

    fun addBrackets() {
        // check last added bracket and equation accordingly
        _equation.value = _equation.value + " ( "
    }

    fun updateEquation(value: String) {
        _equation.value = _equation.value + value
    }

    fun canAdd(value: String) : Boolean {
        // Check the input with last added input and send false if wrong value is given
        return true
    }
    fun getAnswer() {
        _equation.value = "Answer"
    }

}