package printer

import model.UserData

class SimplePrinter : Printer {
    /**
     * Prints [users] into std::out with format: ${data.name} -> ${data.emails.joinToString(", ")}.
     */
    override fun print(users: Collection<UserData>) {
        users.forEach { data ->
            println("${data.name} -> ${data.emails.joinToString(", ")}")
        }
    }
}