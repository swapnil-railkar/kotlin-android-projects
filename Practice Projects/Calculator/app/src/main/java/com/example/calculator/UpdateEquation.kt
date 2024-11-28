package com.example.calculator

class UpdateEquation {

    private val symbols = mutableListOf<String>("%","/","x","-","+")
    private val commonFunctions = CommonFunctions()
    private val parseEquation = ParseEquation()

    /**
     *  This method verifies current input against last added input
     *  to make sure that equation doesn't end up in illegal state.
     */
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

    /**
     *  This function parses the input equation and returns opening bracket
     *  or answer till last opening bracket.
     */
    fun getUpdatedValueForBracket(equation: String) : String {

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
                getUpdatedEquation(equation, true)
            } else {
                ""
            }
        }
    }

    /**
     *  This function gets answer of the equation and updates the equation accordingly.
     *  If the equation getting solved is bracketed equation then return value will be
     *  "equation value until opening bracket + answer of bracketed equation"
     *  else whole equation will be replaced by answer.
     */
    fun getUpdatedEquation(equation: String, isClosingBracket : Boolean) : String {
        val contentList = getContentList(equation)
        return if (contentList.isEmpty()) {
            equation
        } else {
            val answer = parseEquation.solve(contentList)
            val lastOpeningBracketIndex = equation.lastIndexOf("(")
            if (lastOpeningBracketIndex != -1) {
                val index = when {
                    isClosingBracket -> lastOpeningBracketIndex
                    else -> lastOpeningBracketIndex + 1
                }
                equation.substring(0, index) + answer
            } else {
                answer
            }
        }
    }

    /**
     *  This function returns List of type list[0] = num1, list[1] = operator list[2] = num2
     *  or empty list other wise.
     */
    private fun getContentList(equation: String) : List<String> {
        val contentArray = commonFunctions.getContentArray(equation)
            .trim()
            .split("\\s".toRegex())
        return if (contentArray.size == 3
                && contentArray[0].toDoubleOrNull() != null
                && symbols.contains(contentArray[1])
                && contentArray[2].toDoubleOrNull() != null) {
            contentArray
        } else {
            emptyList<String>()
        }
    }
}