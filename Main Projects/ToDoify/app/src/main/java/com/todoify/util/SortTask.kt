package com.todoify.util

import com.todoify.data.entity.Task
import java.time.LocalDate
class SortTask {
    fun getTasksForMainScreen(taskList: List<Task>, date: LocalDate): List<Task>{
        return taskList.asSequence()
            .filter { task: Task -> task.createdAt <= date }
            .filter { task: Task -> task.status == TaskState.IN_PROGRESS }
            .sortedBy { task: Task ->  task.title}
            .sortedByDescending { task: Task ->  task.createdAt}
            .sortedByDescending { task: Task -> task.isImportant }.toList()

    }

    fun getTasksForHistoryScreen(taskList: List<Task>, date: LocalDate): List<Task> {
        return taskList.asSequence()
            .filter { task: Task -> (task.removedAt != null) && (task.removedAt <= date) }
            .filter { task: Task -> task.status == TaskState.COMPLETED
                    || task.status == TaskState.REMOVED }
            .sortedBy { task: Task -> task.title }
            .sortedByDescending { task: Task -> task.createdAt }
            .sortedByDescending { task: Task -> task.removedAt }.toList()
    }
}