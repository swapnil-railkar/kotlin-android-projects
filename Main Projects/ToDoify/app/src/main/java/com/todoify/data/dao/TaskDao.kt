package com.todoify.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.todoify.data.entity.Task
import com.todoify.util.TaskState
import com.todoify.util.typeconverter.LocalDateTypeConverter
import com.todoify.util.typeconverter.TaskStateTypeConverter
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
abstract class TaskDao {

    @Insert
    abstract suspend fun addTask(task: Task)

    @Update
    abstract suspend fun updateTask(task: Task)

    @Delete
    abstract suspend fun deleteTask(task: Task)

    @Query(value = "select * from `task` where `status` = :state")
    abstract fun getTodoTasks(
        state: String = TaskStateTypeConverter.toString(TaskState.IN_PROGRESS)
    ): Flow<List<Task>>

    @Query(value = "select * from `task` where `status` in (:states)")
    abstract fun getHistoryTasks(
        states: List<String> = listOf(
            TaskStateTypeConverter.toString(TaskState.COMPLETED),
            TaskStateTypeConverter.toString(TaskState.REMOVED)
        )
    ): Flow<List<Task>>

    @Query(value = "select * from `task` where `id` = :id")
    abstract fun getTaskById(id: Long): Flow<Task>

    @Query(value = "delete from `task` where `id` in (:idsToDelete)")
    abstract suspend fun deleteAllTasks(idsToDelete: List<Long>)

    // Following two are tasks will run as soon as app boot up
    @Query(value = "delete from `task` where `isDaily` = false and `createdAt` <= :expiryDate")
    abstract suspend fun clearTasks(expiryDate: String)

    @Query(value = "update `task` set `status` = :state where `completeBy` < :today")
    abstract suspend fun updateExpiredTasks(
        today: String = LocalDateTypeConverter.toString(LocalDate.now())!!,
        state: String = TaskStateTypeConverter.toString(TaskState.REMOVED)
    )
}