package com.todoify.data.repository

import com.todoify.data.dao.TaskAgeLimitDao
import com.todoify.data.entity.TaskAgeLimit
import kotlinx.coroutines.flow.Flow

class TaskAgeLimitRepository(private val taskAgeLimitDao: TaskAgeLimitDao) {

    fun addInitialRecord(taskAgeLimit: TaskAgeLimit) {
        return taskAgeLimitDao.addInitialRecord(taskAgeLimit)
    }

    fun getCurrentRows(): Flow<Int> {
        return taskAgeLimitDao.getCount()
    }

    fun getCurrentTaskAgeLimit(): Flow<TaskAgeLimit> {
        return taskAgeLimitDao.getCurrentTaskAgeLimit()
    }

    suspend fun updateTaskAgeLimit(taskAgeLimit: TaskAgeLimit) {
        taskAgeLimitDao.updateTaskAgeLimit(taskAgeLimit)
    }
}