package com.example.safenotes.data

data class DefaultCredentials(
    val id: Long = 0L,
    val password: String = "Swapnil@2001",
    val recoveryQuestion : String = "Favourite Sports club",
    val answer : String = "Liverpool"
)

object DefaultCreds {
    val credentials = DefaultCredentials()
    val recoveryQuestions = listOf<String>(
        "Favourite athlete",
        "Favourite celebrity",
        "Favourite Sports club",
        "My hobby"
    )
}
