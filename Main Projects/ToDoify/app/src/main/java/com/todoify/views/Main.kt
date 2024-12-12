package com.todoify.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.IconButton
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.todoify.R
import com.todoify.bottombar.DefaultBottomBar
import com.todoify.commons.RemoveAllTasks
import com.todoify.commons.SearchField
import com.todoify.commons.TaskRemoverSettingsAlert
import com.todoify.commons.TaskTimeStamps
import com.todoify.data.entity.Task
import com.todoify.data.entity.TaskAgeLimit
import com.todoify.data.graph.Graph
import com.todoify.navigation.Screens
import com.todoify.topbars.DefaultTopBar
import com.todoify.util.FetchHelper
import com.todoify.viewmodel.TaskAgeLimitViewModel
import com.todoify.viewmodel.TaskViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(
    taskViewModel: TaskViewModel,
    navController: NavController,
    taskAgeLimitViewModel: TaskAgeLimitViewModel
) {
    val ageLimit =
        taskAgeLimitViewModel.getCurrentTaskAgeLimit().collectAsState(initial = TaskAgeLimit())
    val fetchHelper = FetchHelper(Graph.tasksRepository, ageLimit.value.age)

    var dateContext by remember { mutableStateOf(LocalDate.now()) }
    var showRemoveAllAlert by remember { mutableStateOf(false) }
    var searchTitle by remember { mutableStateOf("") }
    var openSearchBar by remember { mutableStateOf(false) }
    var openAddEditTaskDialog by remember { mutableStateOf(false) }
    var addEditTaskId by remember { mutableLongStateOf(-1L) }
    var openSettings by remember { mutableStateOf(false) }
    var deleteTaskDays by remember { mutableIntStateOf(30) }

    val context = LocalContext.current
    fetchHelper.UpdateExpiredTasks()
    val taskList = fetchHelper.getTodoTasksForDate(date = dateContext, searchTitle)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                DefaultTopBar(
                    onDateChange = {
                        dateContext = it
                    },
                    onSearchStateChange = {
                        openSearchBar = it
                        if (!openSearchBar) {
                            searchTitle = ""
                        }
                    },
                    onClearAllItems = {
                        showRemoveAllAlert = true
                    },
                    onOpenSettings = {
                        openSettings = true
                    }
                )

                if (openSearchBar) {
                    SearchField(onSearchTitle = {
                        searchTitle = it
                    })
                }
            }

        },
        bottomBar = {
            DefaultBottomBar(
                screenContext = Screens.MainScreen.route,
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addEditTaskId = -1L
                    openAddEditTaskDialog = true
                },
                backgroundColor = colorResource(id = R.color.app_default_color)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new task")
            }
        }
    ) {

        if (openSettings) {
            TaskRemoverSettingsAlert(
                currentSetting = ageLimit.value.age,
                onSettingsChanged = { days: Int? ->
                    if (days == null || days < 1) {
                        Toast.makeText(context, "Invalid value", Toast.LENGTH_LONG).show()
                    } else if (days > 365) {
                        Toast.makeText(context, "Value should be less than 365", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        deleteTaskDays = days
                        taskAgeLimitViewModel.updateTaskAgeLimit(deleteTaskDays, ageLimit.value)
                    }
                    openSettings = false
                },
                onDisMissAlert = {
                    openSettings = false
                }
            )
        }

        if (openAddEditTaskDialog) {
            AddEditTask(
                id = addEditTaskId,
                taskViewModel = taskViewModel,
                onDismiss = { openAddEditTaskDialog = false },
                onAddEditComplete = {
                    openAddEditTaskDialog = false
                }
            )
        }

        if (showRemoveAllAlert) {
            RemoveAllTasks(
                content = stringResource(id = R.string.alert_content_main_screen),
                onConfirmation = {
                    taskViewModel.removeAllTasks(taskList.value)
                    showRemoveAllAlert = false
                },
                onDisMissDialog = { showRemoveAllAlert = false }
            )
        }

        LazyColumn(modifier = Modifier.padding(it)) {

            items(items = taskList.value,
                key = { item: Task -> item.id }) { task ->
                val dismissState = rememberDismissState(
                    confirmStateChange = { dismissValue ->
                        if (dismissValue == DismissValue.DismissedToEnd
                            || dismissValue == DismissValue.DismissedToStart
                        ) {
                            taskViewModel.removeTask(task, false)
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background = {},
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.25f) },
                    dismissContent = {
                        TaskItem(
                            task = task,
                            onMarkImportant = { important ->
                                val updatedTask = task.copy(isImportant = important)
                                taskViewModel.updateTask(updatedTask)
                            },
                            onMarkComplete = {
                                taskViewModel.removeTask(task, true)
                            },
                            onClick = {
                                addEditTaskId = task.id
                                openAddEditTaskDialog = true
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    onMarkImportant: (Boolean) -> Unit,
    onMarkComplete: () -> Unit,
    onClick: () -> Unit
) {
    var starColor by remember { mutableStateOf(Color.LightGray) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() },
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {

            TaskTimeStamps(task = task, screenContext = Screens.MainScreen.route)
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                starColor = if (task.isImportant) Color.Yellow else Color.LightGray
                IconButton(onClick = { onMarkImportant(!task.isImportant) }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "mark important", tint = starColor
                    )
                }
                IconButton(onClick = { onMarkComplete() }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "mark as complete", tint = Color.Green
                    )
                }
            }

        }
    }
}


@Composable
@Preview(showBackground = true)
fun MainViewPreview() {
    val taskViewModel: TaskViewModel = viewModel()
    val taskAgeLimitViewModel: TaskAgeLimitViewModel = viewModel()
    val navController = rememberNavController()
    MainView(taskViewModel, navController = navController, taskAgeLimitViewModel)
}