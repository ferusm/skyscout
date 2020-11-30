import io.kotest.core.spec.style.StringSpec
import model.UserDataContainer
import printer.SimplePrinter

class SimplePrinterTest : StringSpec({
    "Printer should be print" {
        val printer = SimplePrinter()
        val data = UserDataContainer()
        data.addEmails("test", listOf("test", "test"))
        data.print(printer)
    }
})