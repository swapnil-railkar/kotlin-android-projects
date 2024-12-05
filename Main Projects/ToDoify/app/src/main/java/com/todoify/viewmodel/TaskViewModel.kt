package com.todoify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoify.data.entity.Task
import com.todoify.data.graph.Graph
import com.todoify.data.repository.TaskRepository
import com.todoify.util.TaskState
import com.todoify.util.UserContext
import com.todoify.util.typeconverter.LocalDateTypeConverter
import com.todoify.util.typeconverter.TaskStateTypeConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(
    private val taskRepository: TaskRepository = Graph.tasksRepository
) : ViewModel() {

    fun removeAllTasks(tasks: List<Task>, userContext: UserContext) {
        tasks.map { item: Task ->
            item.copy(
                status = TaskStateTypeConverter.toString(TaskState.REMOVED),
                removedAt = LocalDateTypeConverter.toString(LocalDate.now())
            )
        }.forEach { item: Task ->
            updateTask(item, userContext)
        }
    }

    fun removeTask(task: Task, isCompleted: Boolean, userContext: UserContext) {
        val taskState = if (isCompleted) TaskState.COMPLETED else TaskState.REMOVED
        val deletedTask = task.copy(
            status = TaskStateTypeConverter.toString(taskState),
            removedAt = LocalDateTypeConverter.toString(LocalDate.now())
        )
        updateTask(deletedTask, userContext)
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun deleteAllTasks(tasks: List<Task>) {
        val idsToDelete = tasks.map { task: Task -> task.id }
        viewModelScope.launch {
            taskRepository.deleteAllTasks(idsToDelete)
        }
    }

    fun updateTask(updatedTask: Task?, userContext: UserContext) {
        if (updatedTask != null) {
            viewModelScope.launch {
                taskRepository.updateTask(updatedTask)
            }
        }

    }

    fun getTaskById(id: Long): Flow<Task> {
        return taskRepository.getTaskById(id)
    }

    fun addTask(task: Task?) {
        if (task != null) {
            viewModelScope.launch {
                taskRepository.addTask(task)
            }
        }
    }

}