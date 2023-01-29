package calculator

fun main() {
    var statusToExit = false
    while (!statusToExit) {
        when(val inputData = Bot().readData()) {
            "/exit" -> {
                statusToExit = true
                Bot().sayBye()
            }
            "" -> continue
            "/help" -> Bot().sayHelp()
            else -> {
                val sumOfInteger = SumOfInteger().returnSumOfInteger(inputData)
                println(sumOfInteger)
            }
        }
    }
}

class Bot {
    fun readData() = readln()
    fun sayBye() = println("Bye!")
    fun sayHelp() = println("The program calculates the sum of numbers")
}

class SumOfInteger {
    fun returnSumOfInteger(inputStringData: String): Int {
        val listOfIntegerAfterCheckFormat = checkListIntegerFormat(inputStringData)
        return listOfIntegerAfterCheckFormat.sum()
    }

    private fun checkListIntegerFormat(inputStringData: String): List<Int> {
        return inputStringData.split(" ").map { it.toInt() }
    }
}