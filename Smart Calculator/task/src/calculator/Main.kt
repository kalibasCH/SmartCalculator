fun main() {
    var statusToExit = false
    while (!statusToExit) {
        val regexOfExpression = "(( ?[+-]*?\\d+)( [+-]*)?)+".toRegex()
        val regexForUnknownCommand = "\\/\\w*".toRegex()
        val inputData = Bot.readData()
        if (regexOfExpression.matches(inputData.replace("\\s".toRegex(), ""))) {
            val sumOfInteger = Calculator().performCalculation(inputData)
            Bot.getAnswer(sumOfInteger)
        } else if (regexForUnknownCommand.matches(inputData)) {
            when (inputData) {
                "/exit" -> {
                    statusToExit = true
                    Bot.sayBye()
                }
                "/help" -> Bot.sayHelp()
                else -> Bot.sayUnknownCommand()
            }
        } else if (inputData.trim() == "") {
            continue
        } else {
            Bot.sayInvalidExpression()
        }
    }
}

object Bot {
    fun getAnswer(answer: Int) = println(answer)
    fun readData() = readln()
    fun sayBye() = println("Bye!")
    fun sayUnknownCommand() = println("Unknown command")
    fun sayInvalidExpression() = println("Invalid expression")
    fun sayHelp() = println(
        """It means that from now on the program must receive the addition + 
        |and subtraction - operators as an input to distinguish operations from each other. 
        |It must support both unary and binary minus operators. Moreover, If the user has entered 
        |several same operators following each other, the program still should work 
        |(like Java or Python REPL). Also, as you remember from school math, two adjacent minus 
        |signs turn into a plus. Therefore, if the user inputs --, it should be read as +; if they 
        |input ----, it should be read as ++, and so on. The smart calculator ought to have such 
        |a feature.""".trimMargin()
    )
}

class Calculator {

    fun performCalculation(inputStringData: String): Int {
        val listOfIntegerAfterCheckFormat = checkListIntegerFormat(inputStringData)
        return listOfIntegerAfterCheckFormat.sum()
    }

    private fun checkListIntegerFormat(inputStringData: String): List<Int> {
        val inputListOfInt = inputStringData.split(" ").map { it }.filter { it != "+" }
        var elementsOfCalculation = inputListOfInt.toMutableList()
        for (element in inputListOfInt) {
            val indexOfElementInList = elementsOfCalculation.indexOf(element)
            when {
                element == "-" -> elementsOfCalculation =
                    multiplyNextIntByMinusOne(elementsOfCalculation)
                checkThatItInt(element) -> continue
                else -> {
                    if (element.length % 2 == 0 && element[0] == '-') {
                        elementsOfCalculation.removeAt(indexOfElementInList)
                    } else if (element.length % 2 == 1 && element[0] == '-') {
                        elementsOfCalculation[indexOfElementInList + 1] =
                            (elementsOfCalculation[indexOfElementInList + 1].toInt() * -1).toString()
                        elementsOfCalculation.removeAt(indexOfElementInList)
                    } else {
                        elementsOfCalculation.removeAt(indexOfElementInList)
                    }

                }
            }
        }
        return elementsOfCalculation.map { it.toInt() }
    }

    private fun multiplyNextIntByMinusOne(listOfElements: MutableList<String>): MutableList<String> {
        listOfElements[listOfElements.indexOf("-") + 1] =
            (listOfElements[listOfElements.indexOf("-") + 1].toInt() * -1).toString()
        listOfElements.removeAt(listOfElements.indexOf("-"))
        return listOfElements
    }

    private fun checkThatItInt(element: String): Boolean {
        return try {
            element.toInt() in (Int.MIN_VALUE..Int.MAX_VALUE)
        } catch (e: Exception) {
            false
        }
    }
}
