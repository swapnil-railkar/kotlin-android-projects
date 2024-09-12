package com.example.calculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel(){
    private val _equation = mutableStateOf("")
    private val updateEquation = UpdateEquation()
    private val parseEquation = ParseEquation()

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
        if (value == ".") {
            _equation.value = _equation.value + "."
        } else if (num != null) {
            _equation.value = _equation.value + value
        } else {
            if (updateEquation.canGetAnswer(_equation.value)) {
                _equation.value = parseEquation.solve(equation.value) + " $value "
            } else {
                _equation.value = _equation.value + " $value "
            }

        }
    }

    fun canAdd(value: String) : Boolean {
        // Check the input with last added input and send false if wrong value is given
        return updateEquation.canAdd(value, _equation.value)
    }

    fun getAnswer() {
        _equation.value = parseEquation.solve(_equation.value)
    }

    fun backSpace() {
        _equation.value = _equation.value.trim().dropLast(1).trim()
    }

}