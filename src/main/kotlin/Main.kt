package org.example
//purose: this allows the launch of graphical interface
import javax.swing.SwingUtilities

//purpose: starts the calculator window
fun main(){
    SwingUtilities.invokeLater {
        CalculatorWindow().isVisible = true
    }
}
// this is what I started with,
// a console calculator that would print answers inputed from the computer.

//fun runConsoleCalculator() {
//    val calculator = Calculator()
//    //purpose used to support modified entities
//    val history =mutableListOf<String>()
//    println("Calculator")
//    while(true) {
//        print("Enter Number")
//        val firstInput = readln()
//        print("operator (+ - * /)")
//        val operator = readln()
//        print("Enter another Number")
//        val secondInput = readln()
//
//        val firstNumber = firstInput.toDoubleOrNull()
//        val secondNumber = secondInput.toDoubleOrNull()
//        if (firstNumber == null || secondNumber == null) {
//            println("Error: use valid number")
//            continue
//
//        }
//        try {
//            val result = calculator.calculate(firstNumber, operator, secondNumber)
//            println("Result: $result")
//            history.add("$firstNumber $operator $secondNumber = $result")
//        } catch (error: IllegalArgumentException) {
//            println("Error: ${error.message}")
//        }
//        print("Calculate again? (y/n)")
//        val again = readln()
//        if(again.lowercase() != "y") {
//            println("Thanks! Have a good day.")
//            println("\nCalculator History:")
//            for (entry in history) {
//                println(entry)
//            }
//            break
//
//        }
//    }
//}

