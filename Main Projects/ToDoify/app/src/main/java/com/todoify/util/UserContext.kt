package com.todoify.util

import java.time.LocalDate

data class UserContext(
    val date: LocalDate,
    val screen: String,
    val searchInput: String,
)
