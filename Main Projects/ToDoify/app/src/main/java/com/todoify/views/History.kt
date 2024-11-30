package com.todoify.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.todoify.R
import com.todoify.bottombar.DefaultBottomBar
import com.todoify.commons.RemoveAllTasks
import com.todoify.commons.TaskTimeStamps
import com.todoify.data.entity.Task
import com.todoify.navigation.Screens
import com.todoify.topbars.DefaultTopBar
import com.todoify.util.TaskState
import com.todoify.util.UserContext
import com.todoify.viewmodel.TaskViewModel
import java.time.LocalDate

@Composable
fun HistoryView(
    taskViewModel: TaskViewModel,
    navController: NavController
) {
    var dateContext by remember { mutableStateOf(LocalDate.now()) }
    var searchTitle by remember { mutableStateOf("") }
    var historyUserContext = UserContext(
        date = dateContext,
        screen = Screens.HistoryScreen.route,
        searchInput = searchTitle
    )
    var taskList by remember {
        mutableStateOf(taskViewModel.getTaskListForScreen(historyUserContext))
    }
    var displayRemoveAllAlert by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DefaultTopBar(
                onDateChange = {
                    dateContext = it
                    historyUserContext = historyUserContext.copy(date = dateContext)
                    taskList = taskViewModel.getTaskListForScreen(historyUserContext)
                },
                onSearchTitle = {
                    searchTitle = it
                    historyUserContext = historyUserContext.copy(searchInput = searchTitle)
                    taskList = taskViewModel.getTaskListForScreen(historyUserContext)
                },
                onClearAllItems = {
                    displayRemoveAllAlert = true
                }
            )
        },
        bottomBar = {
            DefaultBottomBar(
                screenContext = Screens.HistoryScreen.route,
                navController = navController
            )
        }
    ) {

        if (displayRemoveAllAlert) {
            RemoveAllTasks(content = stringResource(id = R.string.alert_content_history_screen),
                onDisMissDialog = { displayRemoveAllAlert = false },
                onConfirmation = {
                    taskViewModel.deleteAllTasks()
                    taskList = taskViewModel.getTaskListForScreen(historyUserContext)
                    displayRemoveAllAlert = false
                }
            )
        }

        LazyColumn(modifier = Modifier.padding(it)) {
            items(items = taskList, key = { item: Task -> item.id }) { task ->
                HistoryTask(task = task) {
                    taskViewModel.deleteTask(task)
                    taskList = taskViewModel.getTaskListForScreen(userContext = historyUserContext)
                }
            }
        }

    }
}

@Composable
private fun HistoryTask(
    task: Task,
    onDelete: () -> Unit
) {
    var openTaskDescription by remember { mutableStateOf(false) }
    var showDeleteTaskDialogue by remember { mutableStateOf(false) }

    if (showDeleteTaskDialogue) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDeleteTaskDialogue = false },
            confirmButton = {
                TextButton(onClick = { onDelete() }) {
                    Text(text = "Delete", color = colorResource(id = R.color.app_default_color))
                }
            },
            text = {
                Text(text = stringResource(id = R.string.alert_content_history_screen))
            }
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { openTaskDescription = true },
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(4.dp)
            ) {
                TaskTimeStamps(task = task, screenContext = Screens.HistoryScreen.route)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showDeleteTaskDialogue = true }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete task")
                    }
                    if (task.status == TaskState.COMPLETED) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null, tint = Color.Green
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null, tint = Color.Red
                        )
                    }

                }
            }

            if (openTaskDescription) {
                TaskDescriptionView(task = task) {
                    openTaskDescription = false
                }
            }
        }

    }
}

@Composable
private fun TaskDescriptionView(
    task: Task,
    onClickExit: () -> Unit
) {
    val description = task.description ?: "No description found"
    val scrollState = rememberScrollState()


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(4.dp)
                .verticalScroll(scrollState)
        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            TextButton(onClick = { onClickExit() }) {
                Text(text = "Exit", color = colorResource(id = R.color.app_default_color))
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun HistoryTaskPreview() {
    val taskItem = Task(
        title = "TTTTTThhhhiiiisssssss iiiiiiiiisssssssss aaaaaaaaa llllllllooooooonnnnnggggg ttttttiiiiitttttttllllllllleeeeeee",
        description = "Ballast cutlass to go on account transom blow the man down Brethren of the Coast provost trysail hearties Jolly Roger. Tack yardarm wench Spanish Main rope's end lateen sail parley line hands smartly. Ho yard bilged on her anchor clap of thunder doubloon wherry Jack Ketch bucko heave to ahoy.\n" +
                "\n" +
                "Shiver me timbers killick league fore ahoy hardtack belaying pin clap of thunder bring a spring upon her cable measured fer yer chains. Dance the hempen jig loot provost boom Arr yo-ho-ho long clothes draught aye spike. Weigh anchor yawl swab Gold Road barkadeer Sail ho cog wherry Davy Jones' Locker pressgang.\n" +
                "\n" +
                "Swab splice the main brace heave to booty jib yard fore flogging fathom aft. Jib topmast careen hempen halter scurvy Jack Tar hardtack hands take a caulk Letter of Marque. Keelhaul Sink me port yo-ho-ho quarter take a caulk red ensign handsomely haul wind draught.",
        createdAt = LocalDate.now(),
        completeBy = LocalDate.now().plusDays(3),
        isImportant = false,
        isDaily = false,
        status = TaskState.REMOVED,
        removedAt = LocalDate.now()
    )
    HistoryTask(task = taskItem) {

    }
}