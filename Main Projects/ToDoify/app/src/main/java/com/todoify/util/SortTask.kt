package com.todoify.util

import androidx.compose.runtime.State
import com.todoify.data.entity.Task
import com.todoify.util.typeconverter.LocalDateTypeConverter
import com.todoify.util.typeconverter.TaskStateTypeConverter
import java.time.LocalDate

class SortTask {
    fun getTasksForMainScreen(
        taskList: State<List<Task>>,
        date: LocalDate
    ): List<Task> {
        return taskList.value.asSequence()
            .filter { task: Task ->
                LocalDateTypeConverter.toLocalDate(task.createdAt)!! <= date
                        && TaskStateTypeConverter.toTaskState(task.status) == TaskState.IN_PROGRESS
            }
            .sortedBy { task: Task -> task.title }
            .sortedByDescending { task: Task -> task.createdAt }
            .sortedByDescending { task: Task -> task.isImportant }.toList()

    }

    fun getTasksForHistoryScreen(
        taskList: State<List<Task>>,
        date: LocalDate
    ): List<Task> {
        return taskList.value.asSequence()
            .filter { task: Task ->
                task.removedAt != null
                        && LocalDateTypeConverter.toLocalDate(task.removedAt)!! <= date
                        && (TaskStateTypeConverter.toTaskState(task.status) == TaskState.COMPLETED
                        || TaskStateTypeConverter.toTaskState(task.status) == TaskState.REMOVED)
            }
            .sortedBy { task: Task -> task.title }
            .sortedByDescending { task: Task -> task.createdAt }
            .sortedByDescending { task: Task -> task.removedAt }.toList()
    }
}