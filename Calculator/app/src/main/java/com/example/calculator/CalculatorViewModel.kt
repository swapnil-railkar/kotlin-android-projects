package com.example.calculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel(){
    private val _equation = mutableStateOf("")
    private val updateEquation = UpdateEquation()
    val equation : State<String> = _equation

    fun clearEquation() {
        _equation.value = ""
    }

    fun addBrackets() {
        // check last added bracket and equation accordingly
        val bracket = updateEquation.getBracket(_equation.value)
        if (bracket.isNotBlank()) {
            _equation.value = _equation.value + " $bracket "
        }
    }

    fun updateEquation(value: String) {
        val num = value.toIntOrNull()
        val valueToAppend : String = if (value == ".") {
            "."
        } else if (num != null) {
            value
        } else {
            " $value "
        }
        _equation.value = _equation.value + valueToAppend
    }

    fun canAdd(value: String) : Boolean {
        // Check the input with last added input and send false if wrong value is given
        return updateEquation.canAdd(value, _equation.value)
    }

    fun getAnswer() {
        _equation.value = "Answer"
    }

}