package com.todoify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
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
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(
    private val taskRepository: TaskRepository = Graph.tasksRepository
) : ViewModel() {

    private lateinit var _taskList: Flow<List<Task>>
    private lateinit var _todoTaskList: Flow<List<Task>>
    private lateinit var _historyTaskList: Flow<List<Task>>
    private val _sortTask = SortTask()


    init {
        viewModelScope.launch {
            _taskList = taskRepository.getAllTasks()
            _todoTaskList = taskRepository.getTodoTasks()
            _historyTaskList = taskRepository.getHistoryTasks()
        }
    }

    fun getTasksForScreen(screen: String) : Flow<List<Task>> {
        return when(screen) {
            Screens.MainScreen.route -> _todoTaskList
            Screens.HistoryScreen.route -> _historyTaskList
            else -> emptyFlow()
        }
    }

    fun getTaskListForScreen(userContext: UserContext, tasks: List<Task>): List<Task> {
        val taskList = if (userContext.screen == Screens.MainScreen.route) {
            _sortTask.getTasksForMainScreen(tasks, userContext.date)
        } else {
            _sortTask.getTasksForHistoryScreen(tasks, userContext.date)
        }
        return getFilteredTasks(userContext, taskList)
    }

    private fun getFilteredTasks(userContext: UserContext, taskList: List<Task>): List<Task> {
        return if (userContext.searchInput.isBlank() || userContext.searchInput.isEmpty()) {
            taskList
        } else {
            taskList.filter { item: Task ->
                item.title.contains(userContext.searchInput)
            }
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
           viewModelScope.launch{
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