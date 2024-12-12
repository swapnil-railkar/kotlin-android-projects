package com.todoify.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.todoify.data.entity.TaskAgeLimit
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskAgeLimitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addInitialRecord(initialRecord: TaskAgeLimit)

    @Update
    abstract suspend fun updateTaskAgeLimit(taskAgeLimit: TaskAgeLimit)

    @Query(value = "select * from `TaskAgeLimit` limit 1")
    abstract fun getCurrentTaskAgeLimit(): Flow<TaskAgeLimit>

    @Query(value = "select count(*) from `TaskAgeLimit`")
    abstract fun getCount() : Flow<Int>
}