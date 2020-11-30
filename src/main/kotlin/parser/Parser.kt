package parser

import model.UserDataContainer

interface Parser {
    fun parse(rawInputArray: Array<String>): UserDataContainer
}