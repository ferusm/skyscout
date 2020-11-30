import parser.SimpleParser
import printer.SimplePrinter

fun main(argv: Array<String>) {
    val parser = SimpleParser()
    val result = parser.parse(argv)
    val printer = SimplePrinter()
    result.print(printer)
}