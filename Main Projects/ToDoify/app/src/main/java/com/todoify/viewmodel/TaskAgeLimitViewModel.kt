package com.todoify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoify.data.entity.TaskAgeLimit
import com.todoify.data.graph.Graph
import com.todoify.data.repository.TaskAgeLimitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskAgeLimitViewModel(
    private val taskAgeLimitRepository: TaskAgeLimitRepository = Graph.taskAgeLimitRepository
) : ViewModel() {

    private lateinit var _rows: Flow<Int>

    init {
        viewModelScope.launch {
            _rows = taskAgeLimitRepository.getCurrentRows()
        }
    }

    fun getRows(): Flow<Int> {
        return _rows
    }

    fun getCurrentTaskAgeLimit(): Flow<TaskAgeLimit> {
        return taskAgeLimitRepository.getCurrentTaskAgeLimit()
    }

    fun initializeTaskAgeLimit(rows: Int) {
        if (rows == 0) {
            val initialEntry = TaskAgeLimit()
            taskAgeLimitRepository.addInitialRecord(initialEntry)
        }
    }

    fun updateTaskAgeLimit(newAgeLimit: Int, taskAgeLimit: TaskAgeLimit) {
        val newTaskAgeLimit = taskAgeLimit.copy(age = newAgeLimit)
        viewModelScope.launch {
            taskAgeLimitRepository.updateTaskAgeLimit(newTaskAgeLimit)
        }
    }
}