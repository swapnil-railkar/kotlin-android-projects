package com.todoify.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.todoify.data.entity.Task
import com.todoify.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun initializeFlow(flow: Flow<List<Task>>): State<List<Task>> {
    return flow.collectAsState(initial = emptyList())
}