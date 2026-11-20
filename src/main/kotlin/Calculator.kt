package org.example
import kotlin.math.sqrt
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
    fun percentage(number:Double):Double{
        return number /100.0
    }
    fun square(number:Double):Double {
        return number * number
    }
    fun reciprocal(number:Double):Double {
        if (number == 0.0) {
            throw IllegalArgumentException("Cannot divide by zero")
        }
        return 1.0/number
    }
    fun squareRoot(number:Double):Double {
        if (number < 0.0) {
            throw IllegalArgumentException("Cannot square root a negative number")
        }
        return sqrt(number)
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