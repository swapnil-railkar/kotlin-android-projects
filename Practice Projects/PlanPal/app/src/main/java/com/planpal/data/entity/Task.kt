package com.planpal.data.entity

data class Task(
    val id: Long = 0L,
    val title: String,
    val description: String? = null,
    val createdAt: String,
    val completedAt: String? = null,
    val isDaily: Boolean = false,
    val isImportant: Boolean = false
)

data object DummyTasks {
    val taskList: MutableList<Task> = mutableListOf()
}
