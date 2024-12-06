package com.todoify.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.todoify.data.entity.Task
import com.todoify.data.repository.TaskRepository
import com.todoify.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class FetchTaskHelper(taskViewModel: TaskViewModel, taskRepository: TaskRepository) {
    private lateinit var todoTasksFlow: Flow<List<Task>>
    private lateinit var historyTasksFlow: Flow<List<Task>>
    private val repository = taskRepository
    private val sortTask = SortTask()

    init {
        taskViewModel.viewModelScope.launch {
            todoTasksFlow = taskRepository.getTodoTasks()
            historyTasksFlow = taskRepository.getHistoryTasks()
        }
    }

    @Composable
    fun getTodoTasksForDate(date: LocalDate, search: String): State<List<Task>> {
        val tasksState: State<List<Task>> =
            repository.getTodoTasks().collectAsState(initial = emptyList())
        val filteredTasks = filterTaskByTitle(
            tasks = sortTask.getTasksForMainScreen(tasksState, date),
            search = search
        )
        return MutableStateFlow(filteredTasks).collectAsState()
    }

    @Composable
    fun getHistoryTasksForDate(date: LocalDate, search: String): State<List<Task>> {
        val tasksState: State<List<Task>> =
            repository.getHistoryTasks().collectAsState(initial = emptyList())
        val filteredTasks = filterTaskByTitle(
            tasks = sortTask.getTasksForHistoryScreen(tasksState, date),
            search = search
        )
        return MutableStateFlow(filteredTasks).collectAsState()
    }

    private fun filterTaskByTitle(tasks: List<Task>, search: String): List<Task> {
        return if (search.isEmpty() || search.isBlank()) {
            tasks
        } else {
            tasks.filter { item: Task -> item.title.contains(search) }
        }
    }
}
