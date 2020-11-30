import parser.SimpleParser
import printer.SimplePrinter

fun main() {
    val input = mutableListOf<String>()
    while (true) {
        val line = readLine()
        if (line == null || line.isBlank()) {
            break
        }
        input.add(line)
    }
    val parser = SimpleParser()
    val result = parser.parse(input)
    val printer = SimplePrinter()
    result.print(printer)
}