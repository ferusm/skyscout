package printer

import model.UserData

interface Printer {
    fun print(users: Collection<UserData>)
}