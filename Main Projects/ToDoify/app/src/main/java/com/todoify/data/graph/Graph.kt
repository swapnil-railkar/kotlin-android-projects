package com.todoify.data.graph

import android.content.Context
import androidx.room.Room
import com.todoify.data.database.TodoifyDatabase
import com.todoify.data.repository.TaskAgeLimitRepository
import com.todoify.data.repository.TaskRepository

object Graph {
    private lateinit var database: TodoifyDatabase
    val tasksRepository by lazy { TaskRepository(database.taskDao()) }
    val taskAgeLimitRepository by lazy { TaskAgeLimitRepository(database.taskAgeLimitDao()) }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, TodoifyDatabase::class.java, "todoify_db").build()
    }
}