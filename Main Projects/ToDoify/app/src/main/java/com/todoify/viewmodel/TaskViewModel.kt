package com.todoify.viewmodel

import androidx.lifecycle.ViewModel
import com.todoify.data.entity.DummyTask
import com.todoify.data.entity.Task
import com.todoify.data.graph.Graph
import com.todoify.data.repository.TaskRepository
import com.todoify.navigation.Screens
import com.todoify.util.SortTask
import com.todoify.util.TaskState
import com.todoify.util.UserContext
import java.time.LocalDate

class TaskViewModel(
    private val taskRepository: TaskRepository = Graph.tasksRepository
) : ViewModel() {

    private var _todoTaskList: MutableList<Task> = DummyTask.dummyTasks
    private var _historyTaskList: MutableList<Task> = mutableListOf<Task>()
    private val _sortTask = SortTask()
    private var _nextId = DummyTask.dummyTasks.size

    private fun refreshList(taskList: List<Task>, userContext: UserContext) {
        if (userContext.screen == Screens.MainScreen.route) {
            this._todoTaskList = taskList.toMutableList()
        } else {
            this._historyTaskList = taskList.toMutableList()
        }

    }

    fun getIdForNewTask(): Long
    {
        _nextId += 1
        return _nextId.toLong()
    }

    fun getTaskListForScreen(userContext: UserContext): List<Task> {
        val taskList = if (userContext.screen == Screens.MainScreen.route) {
            _sortTask.getTasksForMainScreen(_todoTaskList, userContext.date)
        } else {
            _sortTask.getTasksForHistoryScreen(_historyTaskList, userContext.date)
        }
        return getFilteredTasks(userContext, taskList)
    }

    private fun getFilteredTasks(userContext: UserContext, taskList: List<Task>): List<Task> {
        return if (userContext.searchInput.isBlank() || userContext.searchInput.isEmpty()) {
            refreshList(taskList, userContext)
            taskList
        } else {
            taskList.filter { item: Task ->
                item.title.contains(userContext.searchInput)
            }
            refreshList(taskList, userContext)
            taskList
        }
    }

    fun removeAllTasks(tasks: List<Task>, userContext: UserContext) {
        tasks.map { item: Task ->
            item.copy(status = TaskState.REMOVED, removedAt = LocalDate.now())
        }.forEach { item: Task ->
            updateTask(item, userContext)
        }
    }

    fun removeTask(task: Task, isCompleted: Boolean, userContext: UserContext) {
        val taskState = if (isCompleted) TaskState.COMPLETED else TaskState.REMOVED
        val deletedTask = task.copy(
            status = taskState,
            removedAt = LocalDate.now()
        )
        _historyTaskList.add(deletedTask)
        updateTask(deletedTask, userContext)
    }

    fun deleteTask(task: Task) {
        _historyTaskList.remove(task)
    }

    fun deleteAllTasks() {
        _historyTaskList.clear()
    }

    fun updateTask(updatedTask: Task?, userContext: UserContext) {
        if (updatedTask != null) {
            _todoTaskList.replaceAll { item: Task ->
                if (item.id == updatedTask.id) updatedTask else item
            }
            refreshList(_todoTaskList, userContext)
        }

    }

    fun getTaskById(id: Long): Task? {
        return _todoTaskList.find { item: Task -> item.id == id }
    }

    fun addTask(task: Task?) {
        if (task != null) {
            _todoTaskList.add(task)
        }
    }

}