package com.todoify.viewmodel

import androidx.lifecycle.ViewModel
import com.todoify.data.DummyTask
import com.todoify.data.Task
import com.todoify.util.SortTask
import com.todoify.util.TaskState
import java.time.LocalDate

class TaskViewModel : ViewModel() {

    private var _taskList: MutableList<Task> = DummyTask.dummyTasks
    private val sortTask = SortTask()

    private fun refreshList(taskList: List<Task>) {
        this._taskList = taskList.toMutableList()
    }

    fun getTaskListForMainScreen(dateContext: LocalDate, titleSearch: String): List<Task> {
        val taskList = sortTask.getTasksForMainScreen(_taskList, dateContext)
        refreshList(taskList)
        if (titleSearch.isBlank() || titleSearch.isEmpty()) {
            return taskList
        }
        return taskList.filter { item: Task ->
            item.title.contains(titleSearch)
        }
    }

    fun getTaskListForHistoryScreen(dateContext: LocalDate, titleSearch: String): List<Task> {
        val taskList = sortTask.getTasksForHistoryScreen(_taskList, dateContext)
        refreshList(taskList)
        if (titleSearch.isBlank() || titleSearch.isEmpty()) {
            return taskList
        }
        return taskList.filter { item: Task ->
            item.title.contains(titleSearch)
        }
    }

    fun removeAllTasks(tasks: List<Task>) {
        tasks.map { item: Task ->
            item.copy(status = TaskState.REMOVED, removedAt = LocalDate.now())
        }.forEach { item: Task ->
            updateTask(item)
        }
    }

    fun deleteTask(task: Task, isCompleted: Boolean) {
        val taskState = if (isCompleted) TaskState.COMPLETED else TaskState.REMOVED
        val deletedTask = task.copy(
            status = taskState,
            removedAt = LocalDate.now()
        )
        updateTask(deletedTask)
    }

    fun updateTask(updatedTask: Task) {
        _taskList.replaceAll { item: Task ->
            if (item.id == updatedTask.id) updatedTask else item
        }
        refreshList(_taskList)
    }

}