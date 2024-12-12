package com.todoify.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.todoify.data.entity.Task
import com.todoify.data.graph.Graph
import com.todoify.data.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class FetchHelper(
    private val taskRepository: TaskRepository = Graph.tasksRepository,
    private val ageLimit: Int
) {
    private lateinit var todoTasksFlow: Flow<List<Task>>
    private lateinit var historyTasksFlow: Flow<List<Task>>
    private val sortTask = SortTask()
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        scope.launch {
            todoTasksFlow = taskRepository.getTodoTasks()
            historyTasksFlow = taskRepository.getHistoryTasks()
        }
    }

    @Composable
    fun getTodoTasksForDate(date: LocalDate, search: String): State<List<Task>> {
        val tasksState: State<List<Task>> =
            taskRepository.getTodoTasks().collectAsState(initial = emptyList())
        val filteredTasks = filterTaskByTitle(
            tasks = sortTask.getTasksForMainScreen(tasksState, date),
            search = search
        )
        return MutableStateFlow(filteredTasks).collectAsState()
    }

    @Composable
    fun getHistoryTasksForDate(date: LocalDate, search: String): State<List<Task>> {
        val tasksState: State<List<Task>> =
            taskRepository.getHistoryTasks().collectAsState(initial = emptyList())
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

    @Composable
    fun UpdateExpiredTasks() {
        update(ageLimit)
    }

    private fun update(ageLimit: Int) {
        scope.launch {
            taskRepository.clearTasks(ageLimit)
        }
    }
}
