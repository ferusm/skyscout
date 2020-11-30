package parser

import model.UserDataContainer

interface Parser {
    fun parse(rawInputArray: Collection<String>): UserDataContainer
}