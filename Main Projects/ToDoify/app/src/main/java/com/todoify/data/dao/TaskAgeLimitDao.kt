package com.todoify.data.dao

import androidx.room.Query
import androidx.room.Update
import com.todoify.data.entity.TaskAgeLimit
import kotlinx.coroutines.flow.Flow

abstract class TaskAgeLimitDao {

    @Update
    abstract suspend fun updateTaskAgeLimit(taskAgeLimit: TaskAgeLimit)

    @Query(value = "select * from `TaskAgeLimit`")
    abstract fun getCurrentTaskAgeLimit(): Flow<TaskAgeLimit>

}