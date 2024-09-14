package com.example.calculator

class ParseEquation {

    /**
     *  This function accepts List of type list[0] = num1, list[1] = operator list[2] = num2
     *  and returns calculated value.
     */
    fun solve(contentArray: List<String>): String {
        val num1 = contentArray[0].trim().toDoubleOrNull() ?: 0.0
        val operator = contentArray[1].trim()
        val num2 = contentArray[2].trim().toDoubleOrNull() ?: 0.0

        return applyOperation(num1, num2, operator).toString()
    }

    /**
     *  This function applies the operation and returns calculated value.
     */
    private fun applyOperation(num1 : Double, num2 : Double, operator : String) : Double {
        return when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "x" -> num1 * num2
            "/" -> num1 / num2
            "%" -> (num1/100) * num2
            else -> 0.0
        }
    }

}