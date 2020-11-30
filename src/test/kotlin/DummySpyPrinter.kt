import model.UserData
import printer.Printer

class DummySpyPrinter : Printer {
    var users: Collection<UserData>? = null
    override fun print(users: Collection<UserData>) {
        this.users = users
    }

}