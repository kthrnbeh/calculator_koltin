package org.example

import java.awt.BorderLayout
import java.awt.Font
import javax.swing.*
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class CalculatorWindow: JFrame("Calculator") {
    private val calculator: Calculator = Calculator()
    private val display = JTextField("0")
    private val historyArea = JTextArea(5, 20)
    private var firstNumber: Double? = null
    private var selectedOperator: String? = null
    private var startNewNumber = true

    init {
        setSize(400, 550)
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        layout = BorderLayout(10, 10)
        display.font = Font("Arial", Font.BOLD, 30)
        display.horizontalAlignment = JTextField.RIGHT
        display.isEditable = false
        add(display, BorderLayout.NORTH)
        val buttonPanel = JPanel(GridLayout(5, 4, 5, 5))
        val buttons = listOf(
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", ".", "+",
            "%", "x²", "1/x", "√"
        )
        for (label in buttons) {
            val button = JButton(label)
            button.addActionListener {
                handleButton(label)
            }
            buttonPanel.add(button)
        }
        add(buttonPanel, BorderLayout.CENTER)
        val bottomPanel = JPanel(BorderLayout())
        val backspaceButton = JButton("⌫")
        backspaceButton.addActionListener {
            handleButton("⌫")
        }
        val signButton = JButton("+/-")
        signButton.addActionListener {
            handleButton("+/-")
        }
        val equalsButton = JButton("=")
        equalsButton.addActionListener {
            handleButton("=")
        }
        val clearEntryButton = JButton ("CE")
        clearEntryButton.addActionListener {
            handleButton("CE")
        }
        val controlPanel = JPanel(GridLayout(1, 4, 5, 5))
        controlPanel.add(backspaceButton)
        controlPanel.add(signButton)
        controlPanel.add(equalsButton)
        bottomPanel.add(controlPanel, BorderLayout.NORTH)
        historyArea.isEditable = false
        val clearHistoryButton = JButton("Clear History")
        clearHistoryButton.addActionListener {
            historyArea.text = ""
        }
        bottomPanel.add(JScrollPane(historyArea), BorderLayout.CENTER)
        bottomPanel.add(clearHistoryButton, BorderLayout.SOUTH)
        add(bottomPanel, BorderLayout.SOUTH)
        setupKeyboard()
    }

    private fun handleButton(label: String) {
        when (label) {
            "√" -> {
                val number = display.text.toDoubleOrNull() ?:return
                try{
                    val result = calculator.squareRoot(number)
                    display.text = result.toString()
                    startNewNumber = false
                }catch(error: IllegalArgumentException){
                    display.text = "Error"
                    startNewNumber = true
                }
            }
            "1/x" -> {
                val number = display.text.toDoubleOrNull() ?: return
                try {
                    val result= calculator.reciprocal(number)
                    display.text = result.toString()
                    startNewNumber = false
                }catch(error: IllegalArgumentException){
                    display.text = "Error"
                    startNewNumber = true

                }
            }
            "x²" -> {
                val number = display.text.toDoubleOrNull() ?: return
                val result = calculator.square(number)

                display.text = result.toString()
                startNewNumber = false
            }

            "%" -> {
                val number = display.text.toDoubleOrNull() ?: return
                val result = calculator.percentage(number)

                display.text = result.toString()
                startNewNumber = false
            }

            "+/-" -> {
                val current = display.text.toDoubleOrNull()
                if (current != null && current != 0.0) {
                    display.text = (-current).toString()
                    startNewNumber = false
                }
            }

            "⌫" -> {
                val shortened =display.text.dropLast(1)
                display.text = if (display.text.length > 1 && display.text != "Error"&& shortened != "-") {
                    shortened
                } else {
                   "0"
                }
                startNewNumber = false
            }
            "CE" ->{
                display.text = "0"
                startNewNumber = true
            }

            "C" -> {
                display.text = "0"
                firstNumber = null
                selectedOperator = null
                startNewNumber = true
            }

            in "0".."9", "." -> {
                if (label == "." && display.text.contains(".") && !startNewNumber) {
                    return
                }
                display.text = if (startNewNumber) {
                    if (label == ".") "0." else label
                } else {
                    if (display.text == "0" && label != ".") {
                        label
                    } else {
                        display.text +label
                    }
                }
                startNewNumber = false
            }

            "+", "-", "*", "/" -> {
                firstNumber = display.text.toDoubleOrNull()
                selectedOperator = label
                startNewNumber = true
            }

            "=" -> calculateResult()
        }
    }

    private fun calculateResult() {
        if(startNewNumber) return
        val first = firstNumber ?: return
        val second = display.text.toDoubleOrNull() ?: return
        val operator = selectedOperator ?: return
        try {
            val result = calculator.calculate(first, operator, second)
            historyArea.append("$first $operator $second = $result\n")
            display.text = result.toString()
            startNewNumber = true
        } catch (error: IllegalArgumentException) {
            display.text = "Error"
            startNewNumber = true
        }
        firstNumber = null
        selectedOperator = null
    }

    private fun setupKeyboard() {
        val inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        val actionMap = rootPane.actionMap
        val keys = "0123456789.+-*/"
        for (key in keys) {
            val label = key.toString()
            val actionName = "key_$label"
            inputMap.put(KeyStroke.getKeyStroke(key), actionName)

            actionMap.put(actionName, object : AbstractAction() {
                override fun actionPerformed(event: java.awt.event.ActionEvent?) {
                    handleButton(label)
                }
            })
        }
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "equals")
        actionMap.put("equals", object : AbstractAction() {
            override fun actionPerformed(event: java.awt.event.ActionEvent?) {
                handleButton("=")

            }
        })
    }
}



