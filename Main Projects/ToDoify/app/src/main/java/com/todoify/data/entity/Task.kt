package com.todoify.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.todoify.util.TaskState
import java.time.LocalDate

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "description")
    val description : String?,

    @ColumnInfo(name = "createdAt")
    val createdAt : LocalDate,

    @ColumnInfo(name = "completeBy")
    val completeBy : LocalDate?,

    @ColumnInfo(name = "removedAt")
    val removedAt : LocalDate?,

    @ColumnInfo(name = "isImportant")
    val isImportant : Boolean,

    @ColumnInfo(name = "isDaily")
    val isDaily : Boolean,

    @ColumnInfo(name = "status")
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
            title = "TTTTTThhhhiiiisssssss iiiiiiiiisssssssss aaaaaaaaa llllllllooooooonnnnnggggg ttttttiiiiitttttttllllllllleeeeeee",
            description = "Ballast cutlass to go on account transom blow the man down Brethren of the Coast provost trysail hearties Jolly Roger. Tack yardarm wench Spanish Main rope's end lateen sail parley line hands smartly. Ho yard bilged on her anchor clap of thunder doubloon wherry Jack Ketch bucko heave to ahoy.\n" +
                    "\n" +
                    "Shiver me timbers killick league fore ahoy hardtack belaying pin clap of thunder bring a spring upon her cable measured fer yer chains. Dance the hempen jig loot provost boom Arr yo-ho-ho long clothes draught aye spike. Weigh anchor yawl swab Gold Road barkadeer Sail ho cog wherry Davy Jones' Locker pressgang.\n" +
                    "\n" +
                    "Swab splice the main brace heave to booty jib yard fore flogging fathom aft. Jib topmast careen hempen halter scurvy Jack Tar hardtack hands take a caulk Letter of Marque. Keelhaul Sink me port yo-ho-ho quarter take a caulk red ensign handsomely haul wind draught.",
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
