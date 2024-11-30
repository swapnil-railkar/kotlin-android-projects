package com.todoify.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.todoify.data.dao.TaskDao
import com.todoify.data.entity.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TodoifyDatabase: RoomDatabase() {
    abstract fun taskDao() : TaskDao
}