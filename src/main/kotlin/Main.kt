package org.example

import javax.swing.SwingUtilities

//purpose this area allows users to pick a number
//I also demo here that in terminal you can just input a number, and
// a operation and it will give an answer.
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
    }
}
fun main(){
    SwingUtilities.invokeLater {
        CalculatorWindow().isVisible = true
    }
}
