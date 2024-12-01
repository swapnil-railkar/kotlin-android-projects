package com.todoify.util.typeconverter

import androidx.room.TypeConverter
import com.todoify.util.TaskState

object TaskStateTypeConverter {

    private val stringToTaskStateMap = mapOf(
        "IN_PROGRESS" to TaskState.IN_PROGRESS,
        "COMPLETED" to TaskState.COMPLETED,
        "REMOVED" to TaskState.REMOVED
    )

    private val taskStateToStringMap = mapOf(
        TaskState.IN_PROGRESS to "IN_PROGRESS",
        TaskState.COMPLETED to "COMPLETED",
        TaskState.REMOVED to "REMOVED",
    )

    @TypeConverter
    fun toTaskState(taskState: String): TaskState {
        return stringToTaskStateMap[taskState]!!
    }

    @TypeConverter
    fun toString(taskState: TaskState): String {
        return taskStateToStringMap[taskState]!!
    }
}