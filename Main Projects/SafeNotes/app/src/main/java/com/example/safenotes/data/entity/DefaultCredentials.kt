package com.example.safenotes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "default_credentials")
data class DefaultCredentials(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "recovery_question")
    val recoveryQuestion : String,

    @ColumnInfo(name = "answer")
    val answer : String
)
