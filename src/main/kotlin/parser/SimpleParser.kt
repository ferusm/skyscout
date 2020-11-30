package parser

import exception.ParseException
import model.UserDataContainer
import org.apache.commons.validator.routines.EmailValidator

class SimpleParser : Parser {
    /**
     * Parsing [rawInputArray] into [UserDataContainer].
     * @return parsing result.
     */
    override fun parse(rawInputArray: Collection<String>): UserDataContainer {
        val container = UserDataContainer()
        rawInputArray.forEach { row ->
            if (row.isBlank()) {
                throw ParseException("Input row cannot be blank")
            }
            var name: String? = null
            val emails = mutableSetOf<String>()
            var anchor = 0
            row.forEachIndexed { index, char ->
                when (char) {
                    '-' -> {
                        name = row.substring(anchor, index).trim()
                        if (name!!.isBlank()) {
                            throw ParseException("Username cannot be blank")
                        }
                    }
                    '>' -> {
                        if (name == null) {
                            throw ParseException("Symbol '>' must be after '-'")
                        }
                        anchor = index + 1
                    }
                    ',' -> {
                        if (name == null) {
                            throw ParseException("Username must be before any email")
                        }
                        val mail = row.substring(anchor, index).trim()
                        if (EmailValidator.getInstance().isValid(mail)) {
                            emails.add(mail)
                        } else {
                            throw ParseException("Unexpected substring: '$mail'. Check e-mail format")
                        }
                        anchor = index + 1
                    }
                }
            }
            if (anchor > row.lastIndex) {
                throw ParseException("Unexpected symbol ',' before end of line")
            }
            if (name == null) {
                throw ParseException("Username must be before any email")
            }
            val mail = row.slice(anchor..row.lastIndex).trim()
            if (EmailValidator.getInstance().isValid(mail)) {
                emails.add(mail)
            } else {
                throw ParseException("Unexpected substring: '$mail'. Check e-mail format")
            }
            container.addEmails(name!!, emails)
        }
        return container
    }
}