package com.example.safenotes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "content")
    val content : String,

    @ColumnInfo(name = "password")
    var password : String,

    @ColumnInfo(name = "recoveryQuestion")
    var recoveryQuestion : String,

    @ColumnInfo(name = "answer")
    var answer : String,

    @ColumnInfo(name = "usesDefaults")
    val usesDefaults : Boolean = false
)
