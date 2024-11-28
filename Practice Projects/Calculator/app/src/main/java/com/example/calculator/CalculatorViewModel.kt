package com.example.calculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 *  ViewModel class.
 */
class CalculatorViewModel : ViewModel(){
    // value of this variable is reflected on ui.
    private val _equation = mutableStateOf("")
    private val updateEquation = UpdateEquation()

    val equation : State<String> = _equation

    /**
     *  Resets the equation back to empty value.
     */
    fun clearEquation() {
        _equation.value = ""
    }

    /**
     *  check last added value and update equation accordingly.
     */
    fun addBrackets() {
        val value = updateEquation.getUpdatedValueForBracket(_equation.value)
        if (value.isNotBlank()) {
            if (value == "(") {
                _equation.value = _equation.value.trim() + " $value "
            } else {
                // this value is equation until last opening bracket
                // + solved equation until the last opening bracket.
                _equation.value = value
            }
        }
    }

    /**
     *  This function updates equation according to current input value.
     */
    fun updateEquation(value: String) {
        val num = value.toIntOrNull()
        if (value == ".") {
            _equation.value = _equation.value + "."
        } else if (num != null) {
            _equation.value = _equation.value + value
        } else {
            _equation.value = updateEquation
                .getUpdatedEquation(_equation.value, false).trim() + " $value "
        }
    }

    /**
     *  Check the input with last added input and send false if wrong value is given.
     */
    fun canAdd(value: String) : Boolean {
        return updateEquation.canAdd(value, _equation.value)
    }

    /**
     *  Gets answer of equation, assuming the equation is of type "num1 operator num2".
     */
    fun getAnswer() {
        _equation.value = updateEquation.getUpdatedEquation(_equation.value, false)
    }

    /**
     *  Removes most recent input.
     */
    fun backSpace() {
        _equation.value = _equation.value.trim().dropLast(1).trim()
    }

}