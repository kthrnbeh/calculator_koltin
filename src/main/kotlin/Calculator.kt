package org.example
import kotlin.math.sqrt
//purpose of this file is to create the math formula's for the interegers selected
//then I can use them in other files, but seperate so its more organized.
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
    //purpose is to give the reciprical
    //but also make so there is error handling if someone tries to divide by zero
    fun reciprocal(number:Double):Double {
        if (number == 0.0) {
            throw IllegalArgumentException("Cannot divide by zero")
        }
        return 1.0/number
    }
    //purpose is to have the integer give a square root
    //but we also gave error handling because you can't square root a negative number.
    fun squareRoot(number:Double):Double {
        if (number < 0.0) {
            throw IllegalArgumentException("Cannot square root a negative number")
        }
        return sqrt(number)
    }
    //purpose is to link the symbols to what the math problem above are
    //the user can pick +-* instead of words, and makes the calculator look better
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