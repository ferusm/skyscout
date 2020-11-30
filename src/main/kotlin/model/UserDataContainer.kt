package model

import printer.Printer

class UserDataContainer {
    private val emailToUser = mutableMapOf<String, String>()
    private val userDataMap = mutableMapOf<String, UserData>()

    /**
     * Adds a [insertingEmails] for [insertingUsername] with collision processing.
     */
    fun addEmails(insertingUsername: String, insertingEmails: Collection<String>) {
        val insertingData = UserData(insertingUsername).apply { this.emails.addAll(insertingEmails) }
        insertingEmails.map { email -> emailToUser.computeIfAbsent(email) { insertingUsername } }.distinct()
            .forEach { username ->
                if (username != insertingUsername) {
                    val data = userDataMap.remove(username)
                    data?.emails?.forEach { email ->
                        emailToUser[email] = insertingUsername
                        insertingData.emails.add(email)
                    }
                }
            }
        userDataMap[insertingUsername] = insertingData
    }

    fun print(printer: Printer) = printer.print(userDataMap.values)
}