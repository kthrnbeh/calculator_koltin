//purpose allows all the files to work together
package org.example


// Imports Java Swing and AWT tools so I can create
// and organize the calculator's graphical interface.

import java.awt.BorderLayout
import java.awt.Font
import javax.swing.*
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

// purpose this whole area is basically the css or what the calcultor will look like
// and function like it uses the other two files to do the math

class CalculatorWindow: JFrame("Calculator") {
    // purpose private varibles that can only be used in class and not changed
    // gives you a list of components to use throughout your code and alter or add too
    private var lastAnswer: Double? = null
    private val operationLabel =JLabel("Ready")
    private val calculator: Calculator = Calculator()
    private val display = JTextField("0")
    private val historyArea = JTextArea(5, 20)
    private var firstNumber: Double? = null
    private var selectedOperator: String? = null
    private var startNewNumber = true
//Purpose: this runs when the window opens
    // sets up window size, position, layout,font, alignment and operation label
    //shows the window display at the top above all the numbers etc.

    init {
        setSize(400, 550)
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        layout = BorderLayout(10, 10)
        display.font = Font("Arial", Font.BOLD, 30)
        display.horizontalAlignment = JTextField.RIGHT
        display.isEditable = false
        val displayPanel = JPanel(BorderLayout())
        operationLabel.horizontalAlignment = JLabel.RIGHT
        displayPanel.add(operationLabel, BorderLayout.NORTH)
        displayPanel.add(display, BorderLayout.CENTER)
        add(displayPanel, BorderLayout.NORTH)
       // purpose the grid for how the buttons will be layed out
        val buttonPanel = JPanel(GridLayout(5, 4, 5, 5))
        val buttons = listOf(
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", ".", "+",
            "%", "x²", "1/x", "√"
        )
    //purpose to avoid writing similar code 20 times
    // creates a button for each thing in the grid, connects to the handlebutton
        // basically what the button does.
        //then adds the button to the grid, and the place it goes

        for (label in buttons) {
            val button = JButton(label)
            button.addActionListener {
                handleButton(label)
            }
            buttonPanel.add(button)
        }
    //these buttons are outside my grid above so I create their own grid.

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

        val answerButton = JButton ("ANS")
        answerButton.addActionListener {
            handleButton("ANS")
        }
        val controlPanel = JPanel(GridLayout(1, 5, 5, 5))
        controlPanel.add(backspaceButton)
        controlPanel.add(signButton)
        controlPanel.add(equalsButton)
        controlPanel.add(clearEntryButton)
        controlPanel.add(answerButton)
        bottomPanel.add(controlPanel, BorderLayout.NORTH)
        // purpose: keeps user from deleting history, and makes it scrollable.
        historyArea.isEditable = false
        val clearHistoryButton = JButton("Clear History")
        clearHistoryButton.addActionListener {
            historyArea.text = ""
        }
        // make the window for the history area, and where, and looks.
        bottomPanel.add(JScrollPane(historyArea), BorderLayout.CENTER)
        bottomPanel.add(clearHistoryButton, BorderLayout.SOUTH)
        add(bottomPanel, BorderLayout.SOUTH)
        setupKeyboard()
    }
    //purpose of all the handlebuttons is to show what happens when each button is pushed
    //to call the math functions in calculator.kt and use them, and display them

    private fun handleButton(label: String) {
        when (label) {
            "ANS" ->{
                val answer = lastAnswer ?: return
                display.text = answer.toString()
                startNewNumber = false
            }
            "√" -> {
                val number = display.text.toDoubleOrNull() ?:return
                try{
                    val result = calculator.squareRoot(number)
                    lastAnswer = result
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
                    lastAnswer = result
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
                lastAnswer = result

                display.text = result.toString()
                startNewNumber = false
            }

            "%" -> {
                val number = display.text.toDoubleOrNull() ?: return
                val result = calculator.percentage(number)
                lastAnswer = result
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
                operationLabel.text ="Ready"
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
                operationLabel.text = "$firstNumber $label"
                startNewNumber = true
            }


            "=" -> calculateResult()

        }
    }
    // purpose: complete the math problem, calculate the answer, and update the calculator.
    //sends the problem to calculator.kt and saves the answer in the history
    //updates what the display looks like and resets everything
    //if the user inputs something wrong it handles displaying error


    private fun calculateResult() {
        if(startNewNumber) return
        val first = firstNumber ?: return
        val second = display.text.toDoubleOrNull() ?: return
        val operator = selectedOperator ?: return
        try {
            val result = calculator.calculate(first, operator, second)
            lastAnswer = result
            historyArea.append("$first $operator $second = $result\n")
            display.text = result.toString()
            startNewNumber = true
        } catch (error: IllegalArgumentException) {
            display.text = "Error"
            startNewNumber = true
        }
        firstNumber = null
        selectedOperator = null
        operationLabel.text = "Ready"
    }
    // purpose: help numbers work from the keyboard instead of just needing to click them.
    //inputMap is for telling which key was pressed actionMap says what each key will do.
    //but it still uses the same handleButton code above to determine what each button does and what it handles.

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
        inputMap.put (
            KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0),
            "backspace"
        )
        actionMap.put("backspace", object : AbstractAction() {
            override fun actionPerformed(event: java.awt.event.ActionEvent?) {
                handleButton("⌫")
            }
        })
        inputMap.put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            "clear"
        )
        actionMap.put("clear", object : AbstractAction() {
            override fun actionPerformed(event: java.awt.event.ActionEvent?) {
                handleButton("C")
            }
        })




    }
}



