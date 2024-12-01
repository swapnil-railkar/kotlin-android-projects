package com.todoify.util

import com.todoify.data.entity.Task
import com.todoify.util.typeconverter.LocalDateTypeConverter
import com.todoify.util.typeconverter.TaskStateTypeConverter
import java.time.LocalDate

class SortTask {
    fun getTasksForMainScreen(taskList: List<Task>, date: LocalDate): List<Task> {
        return taskList.asSequence()
            .filter { task: Task ->
                LocalDateTypeConverter.toLocalDate(task.createdAt)!! <= date
            }
            .filter { task: Task ->
                TaskStateTypeConverter.toTaskState(task.status) == TaskState.IN_PROGRESS
            }
            .sortedBy { task: Task -> task.title }
            .sortedByDescending { task: Task -> task.createdAt }
            .sortedByDescending { task: Task -> task.isImportant }.toList()

    }

    fun getTasksForHistoryScreen(taskList: List<Task>, date: LocalDate): List<Task> {
        return taskList.asSequence()
            .filter { task: Task ->
                (task.removedAt != null) &&
                        (LocalDateTypeConverter.toLocalDate(task.removedAt)!! <= date)
            }
            .filter { task: Task ->
                TaskStateTypeConverter.toTaskState(task.status) == TaskState.COMPLETED ||
                        TaskStateTypeConverter.toTaskState(task.status) == TaskState.REMOVED
            }
            .sortedBy { task: Task -> task.title }
            .sortedByDescending { task: Task -> task.createdAt }
            .sortedByDescending { task: Task -> task.removedAt }.toList()
    }
}