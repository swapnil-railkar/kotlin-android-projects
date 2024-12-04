package com.todoify.data.repository

import com.todoify.data.dao.TaskDao
import com.todoify.data.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getTodoTasks(): Flow<List<Task>> {
        return taskDao.getTodoTasks()
    }

    fun getHistoryTasks(): Flow<List<Task>> {
        return taskDao.getHistoryTasks()
    }

    fun getTaskById(id: Long): Flow<Task> {
        return taskDao.getTaskById(id)
    }

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks(idsToDelete: List<Long>) {
        taskDao.deleteAllTasks(idsToDelete)
    }

    suspend fun clearTasks() {
        taskDao.clearTasks()
    }

    suspend fun updateExpiredTasks() {
        taskDao.updateExpiredTasks()
    }


}