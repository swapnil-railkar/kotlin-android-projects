package com.todoify.data.repository

import com.todoify.data.dao.TaskDao
import com.todoify.data.entity.Task
import com.todoify.util.typeconverter.LocalDateTypeConverter
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskRepository(private val taskDao: TaskDao) {

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

    suspend fun clearTasks(ageLimit: Int) {
        val expiryDate = LocalDate.now().minusDays(ageLimit.toLong())
        val expiryDateString =  LocalDateTypeConverter.toString(expiryDate)!!
        taskDao.clearTasks(expiryDateString)
    }

    suspend fun updateExpiredTasks() {
        taskDao.updateExpiredTasks()
    }


}