package com.example.safenotes.data

data class DefaultCredentials(
    val id: Long = 0L,
    val password: String,
    val recoveryQuestion : String,
    val answer : String
)
