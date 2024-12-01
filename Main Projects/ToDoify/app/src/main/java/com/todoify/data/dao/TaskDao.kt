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

    @Query(value = "select * from `task`")
    abstract fun getAllTasks(): Flow<List<Task>>

    @Query(value = "select * from `task` where `status` = :state")
    abstract fun getTodoTasks(
        state: String = TaskStateTypeConverter.toString(TaskState.IN_PROGRESS)
    )

    @Query(value = "select * from `task` where `status` in (:states)")
    abstract fun getHistoryTasks(
        states: List<String> = listOf(
            TaskStateTypeConverter.toString(TaskState.COMPLETED),
            TaskStateTypeConverter.toString(TaskState.REMOVED)
        )
    )

    @Query(value = "select * from `task` where `id` = :id")
    abstract fun getTaskById(id: Long): Flow<Task>

    @Query(value = "delete from `task` where `id` in (:idsToDelete)")
    abstract suspend fun deleteAllTasks(idsToDelete: List<Long>)

    // Following two are scheduled tasks, will run once everyday at 00.00
    @Query(value = "delete from `task` where `createdAt` <= :monthAgo")
    abstract suspend fun clearTasks(
        monthAgo: String = LocalDateTypeConverter.toString(LocalDate.now().minusMonths(1))!!
    )

    @Query(value = "update `task` set `status` = :state where `completeBy` < :today")
    abstract suspend fun updateExpiredTasks(
        today: String = LocalDateTypeConverter.toString(LocalDate.now())!!,
        state: String = TaskStateTypeConverter.toString(TaskState.REMOVED)
    )
}