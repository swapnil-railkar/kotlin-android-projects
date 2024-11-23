package com.todoify.data

import com.todoify.util.TaskState
import java.time.LocalDate

data class Task(
    val id : Long = 0,
    val title : String,
    val description : String?,
    val createdAt : LocalDate,
    val completeBy : LocalDate?,
    val removedAt : LocalDate?,
    val isImportant : Boolean,
    val isDaily : Boolean,
    val status : TaskState
)

object DummyTask {
    val dummyTasks = mutableListOf<Task>(
        // Tasks for main screen
        // Task 1
        Task(
            id = 0L,
            title = "abc",
            description = null,
            createdAt = LocalDate.now(),
            completeBy = null,
            removedAt = null,
            isImportant = true,
            isDaily = false,
            status = TaskState.IN_PROGRESS
        ),
        // Task 4
        Task(
            id = 1L,
            title = "sef",
            description = null,
            createdAt = LocalDate.now().minusDays(2L),
            completeBy = null,
            removedAt = null,
            isImportant = true,
            isDaily = false,
            status = TaskState.IN_PROGRESS
        ),
        // Task 3
        Task(
            id = 2L,
            title = "tuv",
            description = null,
            createdAt = LocalDate.now().minusDays(3L),
            completeBy = null,
            removedAt = null,
            isImportant = true,
            isDaily = false,
            status = TaskState.IN_PROGRESS
        ),
        // Task 2
        Task(
            id = 3L,
            title = "ghi",
            description = null,
            createdAt = LocalDate.now(),
            completeBy = null,
            removedAt = null,
            isImportant = true,
            isDaily = false,
            status = TaskState.IN_PROGRESS
        ),
        // Task 5
        Task(
            id = 4L,
            title = "xyz",
            description = null,
            createdAt = LocalDate.now(),
            completeBy = null,
            removedAt = null,
            isImportant = false,
            isDaily = false,
            status = TaskState.IN_PROGRESS
        ),
        // Task 6
        Task(
            id = 5L,
            title = "Abc",
            description = null,
            createdAt = LocalDate.now().minusDays(3L),
            completeBy = null,
            removedAt = null,
            isImportant = false,
            isDaily = false,
            status = TaskState.IN_PROGRESS
        ),

        // Tasks for history screen
        // Task 1
        Task(
            id = 6L,
            title = "xyz",
            description = null,
            createdAt = LocalDate.now().minusDays(2L),
            completeBy = null,
            removedAt = LocalDate.now(),
            isImportant = true,
            isDaily = false,
            status = TaskState.REMOVED
        ),
        // Task 4
        Task(
            id = 7L,
            title = "Acb",
            description = null,
            createdAt = LocalDate.now().minusDays(4L),
            completeBy = null,
            removedAt = LocalDate.now().minusDays(1L),
            isImportant = true,
            isDaily = false,
            status = TaskState.COMPLETED
        ),
        // Task 2
        Task(
            id = 8L,
            title = "Abc",
            description = null,
            createdAt = LocalDate.now().minusDays(3L),
            completeBy = null,
            removedAt = LocalDate.now(),
            isImportant = true,
            isDaily = false,
            status = TaskState.COMPLETED
        ),
        // Task 3
        Task(
            id = 9L,
            title = "efg",
            description = null,
            createdAt = LocalDate.now().minusDays(3L),
            completeBy = null,
            removedAt = LocalDate.now().minusDays(1L),
            isImportant = true,
            isDaily = false,
            status = TaskState.REMOVED
        )
    )
}
