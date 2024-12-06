package com.todoify.data.repository

import com.todoify.data.dao.TaskAgeLimitDao
import com.todoify.data.entity.TaskAgeLimit
import kotlinx.coroutines.flow.Flow

class TaskAgeLimitRepository(private val taskAgeLimitDao: TaskAgeLimitDao) {

    fun getCurrentTaskAgeLimit(): Flow<TaskAgeLimit> {
        return taskAgeLimitDao.getCurrentTaskAgeLimit()
    }

    suspend fun updateTaskAgeLimit(taskAgeLimit: TaskAgeLimit) {
        taskAgeLimitDao.updateTaskAgeLimit(taskAgeLimit)
    }
}