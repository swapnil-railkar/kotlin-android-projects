package com.example.safenotes.data

data class Note(
    val id : Long = 0L,
    val title : String,
    val content : String,
    var password : String,
    var recoveryQuestion : String,
    var answer : String,
    val usesDefaults : Boolean = false
)
