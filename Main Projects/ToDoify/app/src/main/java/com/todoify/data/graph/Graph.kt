package com.todoify.data.graph

import android.content.Context
import androidx.room.Room
import com.todoify.data.database.MainDatabase
import com.todoify.data.repository.TaskAgeLimitRepository
import com.todoify.data.repository.TaskRepository

object Graph {
    private lateinit var database: MainDatabase
    val tasksRepository by lazy { TaskRepository(database.taskDao()) }
    val taskAgeLimitRepository by lazy { TaskAgeLimitRepository(database.taskAgeLimitDao()) }

    fun provide(context: Context) {
        database = Room
            .databaseBuilder(context, MainDatabase::class.java, "main_db")
            .build()
    }
}