package com.todoify.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.todoify.data.dao.TaskAgeLimitDao
import com.todoify.data.dao.TaskDao
import com.todoify.data.entity.Task
import com.todoify.data.entity.TaskAgeLimit

@Database(
    entities = [Task::class, TaskAgeLimit::class],
    version = 2,
    exportSchema = false
)
abstract class MainDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskAgeLimitDao(): TaskAgeLimitDao
}