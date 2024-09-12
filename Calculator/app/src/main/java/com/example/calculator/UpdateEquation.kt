package com.example.calculator

class UpdateEquation {

    private val symbols = mutableListOf<String>("%","/","x","-","+")

    fun canAdd(value : String, equation : String) : Boolean {
        if (equation.isEmpty()) {
            return value.toIntOrNull() != null
        } else {
            val lastValue = equation.trim().last().toString()
            return when {
                (equation.isEmpty() && symbols.contains(value)
                        || (symbols.contains(value) && symbols.contains(lastValue))
                        || (lastValue == ")" && !symbols.contains(value))
                        || (lastValue == "(" && symbols.contains(value))
                        || (lastValue == "." && symbols.contains(value))
                        || (lastValue == "." && (value == "(" || value == ")"))
                        || (lastValue == "/" && (lastValue.toIntOrNull() == 0))
                ) -> false

                else -> true
            }
        }

    }
    
    fun getBracket(equation: String) : String {

        if (equation.isEmpty()) {
            return "("
        } else {
            val stack = ArrayDeque<Char>()
            val str = equation.trim()
            for (value in str) {
                if (value == '(') {
                    stack.addLast('(')
                } else if (value == ')') {
                    stack.removeLast()
                }
            }
            return if (str.last().toString() != ")"
                && symbols.contains(str.last().toString())) {
                "("
            } else if (str.last().toString() == "(") {
                "("
            } else if (str.last().toString().toIntOrNull() != null || !stack.isEmpty()){
                ")"
            } else {
                ""
            }
        }
    }

    fun canGetAnswer(equation: String) : Boolean {
        val contentArray = equation.trim().split("\\s".toRegex())
        return contentArray.size == 3
                && contentArray[0].toDoubleOrNull() != null
                && symbols.contains(contentArray[1])
                && contentArray[2].toDoubleOrNull() != null
    }
}