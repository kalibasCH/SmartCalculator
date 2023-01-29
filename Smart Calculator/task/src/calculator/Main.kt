package calculator

fun main() {
    val listOfInt = readln().split(" ").map { it.toInt() }
    println(listOfInt.sum())
}