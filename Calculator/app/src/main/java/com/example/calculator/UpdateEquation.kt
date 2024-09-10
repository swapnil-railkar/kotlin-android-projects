package com.example.calculator

class UpdateEquation {

    private val symbols = mutableListOf<String>("%","/","X","-","+")

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
                        ) -> false

                else -> true
            }
        }

    }

    // TODO : Work on balancing brackets
    fun getBracket(equation: String) : String {

        if (equation.isEmpty()) {
            return "("
        } else {
            val stack = ArrayDeque<Char>()
            val str = equation.trim().replace("\\s".toRegex(), "")
            for (value in str) {
                if (value == '(') {
                    stack.addLast('(')
                } else if (value == ')') {
                    stack.removeLast()
                }
            }
            return if (stack.isEmpty()
                && str.last().toString() != ")"
                && symbols.contains(str.last().toString())) {
                "("
            } else if (!symbols.contains(str.last().toString())
                && str.last().toString() != "."){
                ")"
            } else {
                ""
            }
        }
    }
}