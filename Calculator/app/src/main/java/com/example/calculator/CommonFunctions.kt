package com.example.calculator

class CommonFunctions {

    private fun getEquationString(equation: String, lastOpeningBracketIndex : Int) : String {
        return if (lastOpeningBracketIndex >= 0) {
            equation.substring(lastOpeningBracketIndex + 1, equation.length)
        } else {
            equation
        }
    }

    fun getContentArray(equation: String): String {
        val lastOpeningBracketIndex = equation.lastIndexOf("(")
        return getEquationString(equation, lastOpeningBracketIndex)
    }
}