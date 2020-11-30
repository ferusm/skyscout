package model

data class UserData(val name: String, val emails: MutableSet<String> = mutableSetOf())