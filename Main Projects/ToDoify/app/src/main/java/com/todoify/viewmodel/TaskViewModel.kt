package com.todoify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoify.data.entity.Task
import com.todoify.data.graph.Graph
import com.todoify.data.repository.TaskRepository
import com.todoify.navigation.Screens
import com.todoify.util.SortTask
import com.todoify.util.TaskState
import com.todoify.util.UserContext
import com.todoify.util.typeconverter.LocalDateTypeConverter
import com.todoify.util.typeconverter.TaskStateTypeConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(
    private val taskRepository: TaskRepository = Graph.tasksRepository
) : ViewModel() {

    private lateinit var _taskList: Flow<List<Task>>
    private lateinit var _todoTaskList: MutableList<Task>
    private lateinit var _historyTaskList: MutableList<Task>
    private val _sortTask = SortTask()


    init {
        viewModelScope.launch {
            _taskList = taskRepository.getAllTasks()
            _taskList.collect(FlowCollector {
                val partitionedList =
                    it.partition { item: Task ->
                        TaskStateTypeConverter.toTaskState(item.status) == TaskState.IN_PROGRESS
                    }
                _todoTaskList = partitionedList.first.toMutableList()
                _historyTaskList = partitionedList.second.toMutableList()
            })
        }
    }

    private fun refreshList(taskList: List<Task>, userContext: UserContext) {
        if (userContext.screen == Screens.MainScreen.route) {
            this._todoTaskList = taskList.toMutableList()
        } else {
            this._historyTaskList = taskList.toMutableList()
        }

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
        _historyTaskList.add(deletedTask)
        updateTask(deletedTask, userContext)
    }

    fun deleteTask(task: Task) {
        _historyTaskList.remove(task)
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        val idsToDelete = _historyTaskList.map { task: Task -> task.id }
        viewModelScope.launch {
            taskRepository.deleteAllTasks(idsToDelete)
        }
        _historyTaskList.clear()
    }

    fun updateTask(updatedTask: Task?, userContext: UserContext) {
        if (updatedTask != null) {
            _todoTaskList.replaceAll { item: Task ->
                if (item.id == updatedTask.id) updatedTask else item
            }
            viewModelScope.launch {
                taskRepository.updateTask(updatedTask)
            }
            refreshList(_todoTaskList, userContext)
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