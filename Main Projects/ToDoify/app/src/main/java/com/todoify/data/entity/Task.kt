package com.todoify.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "createdAt")
    var createdAt: String,

    @ColumnInfo(name = "completeBy")
    val completeBy: String? = null,

    @ColumnInfo(name = "removedAt")
    val removedAt: String? = null,

    @ColumnInfo(name = "isImportant")
    val isImportant: Boolean = false,

    @ColumnInfo(name = "isDaily")
    val isDaily: Boolean = false,

    @ColumnInfo(name = "status")
    val status: String
)

