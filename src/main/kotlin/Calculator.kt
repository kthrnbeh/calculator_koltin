package org.example
class Calculator {
    fun add(first: Double, second: Double): Double {
        return first + second
    }

    fun subtract(first: Double, second: Double): Double {
        return first - second
    }

    fun multiply(first: Double, second: Double): Double {
        return first * second
    }

    fun divide(first: Double, second: Double): Double {
        if (second == 0.0) {
            throw IllegalArgumentException("Cannot divide by zero")
        }
        return first / second
    }

    fun calculate(first: Double, operator: String, second: Double): Double {
        return when (operator) {
            "+" -> add(first, second)
            "-" -> subtract(first, second)
            "*" -> multiply(first, second)
            "/" -> divide(first, second)
            else -> throw IllegalArgumentException("unkown operator")
        }
    }
}