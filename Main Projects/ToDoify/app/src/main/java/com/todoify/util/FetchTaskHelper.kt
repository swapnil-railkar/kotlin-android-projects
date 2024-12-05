package com.todoify.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.todoify.data.entity.Task
import com.todoify.data.repository.TaskRepository
import com.todoify.util.typeconverter.LocalDateTypeConverter
import com.todoify.util.typeconverter.TaskStateTypeConverter
import com.todoify.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

private data object InitialStates {
    val initialTaskListState: List<Task> = emptyList()
    val initialTaskState: Task = Task(
        title = "",
        createdAt = LocalDateTypeConverter.toString(LocalDate.now())!!,
        status = TaskStateTypeConverter.toString(TaskState.IN_PROGRESS)
    )
}

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
    fun getTodoTasksForDate(date: LocalDate): State<List<Task>> {
        val tasksState: State<List<Task>> =
            repository.getTodoTasks().collectAsState(initial = InitialStates.initialTaskListState)
        return MutableStateFlow(sortTask.getTasksForMainScreen(tasksState, date)).collectAsState()
    }

    @Composable
    fun getHistoryTasksForDate(date: LocalDate): State<List<Task>> {
        val tasksState: State<List<Task>> =
        repository.getHistoryTasks().collectAsState(initial = InitialStates.initialTaskListState)
        return MutableStateFlow(sortTask.getTasksForHistoryScreen(tasksState, date)).collectAsState()
    }
}
