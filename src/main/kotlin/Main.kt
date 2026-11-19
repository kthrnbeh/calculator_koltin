package org.example

import javax.swing.SwingUtilities


fun runConsoleCalculator() {
    val calculator = Calculator()
    //purpose used to support modified entities
    val history =mutableListOf<String>()
    println("Calculator")
    while(true) {
        print("Enter Number")
        val firstInput = readln()
        print("operator (+ - * /)")
        val operator = readln()
        print("Enter another Number")
        val secondInput = readln()

        val firstNumber = firstInput.toDoubleOrNull()
        val secondNumber = secondInput.toDoubleOrNull()
        if (firstNumber == null || secondNumber == null) {
            println("Error: use valid number")
            continue

        }
        try {
            val result = calculator.calculate(firstNumber, operator, secondNumber)
            println("Result: $result")
            history.add("$firstNumber $operator $secondNumber = $result")
        } catch (error: IllegalArgumentException) {
            println("Error: ${error.message}")
        }
        print("Calculate again? (y/n)")
        val again = readln()
        if(again.lowercase() != "y") {
            println("Thanks! Have a good day.")
            println("\nCalculator History:")
            for (entry in history) {
                println(entry)
            }
            break

        }
        /*
        try {
            calculator.divide(10.0, 0.0)
        } catch (error: IllegalArgumentException) {
            println("error: ${error.message}")
        }
        val selectedOperator = "+"
        try {
            val result = calculator.calculate(8.0, selectedOperator, 3.0)
            println("selected operator: $selectedOperator")
            println("Result: $result")
        } catch (error: IllegalArgumentException) {
            println("error: ${error.message}")
        }*/
    }
}
fun main(){
    SwingUtilities.invokeLater {
        CalculatorWindow().isVisible = true
    }
}
