package com.todoify.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.todoify.R
import com.todoify.bottombar.DefaultBottomBar
import com.todoify.commons.RemoveAllTasks
import com.todoify.commons.TaskTimeStamps
import com.todoify.data.Task
import com.todoify.navigation.Screens
import com.todoify.topbars.DefaultTopBar
import com.todoify.util.UserContext
import com.todoify.viewmodel.TaskViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(
    taskViewModel: TaskViewModel,
    navController: NavController
) {
    var dateContext by remember { mutableStateOf(LocalDate.now()) }
    var showRemoveAllAlert by remember { mutableStateOf(false) }
    var searchTitle by remember { mutableStateOf("") }
    var userContext = UserContext(
        date = dateContext,
        screen = Screens.MainScreen.route,
        searchInput = searchTitle
    )
    var taskList by remember {
        mutableStateOf(
            taskViewModel
                .getTaskListForScreen(userContext)
        )
    }
    var openAddEditTaskDialog by remember { mutableStateOf(false) }
    var addEditTaskId by remember { mutableLongStateOf(-1L) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopBar(
                onDateChange = {
                    dateContext = it
                    userContext = userContext.copy(date = dateContext)
                    taskList = taskViewModel.getTaskListForScreen(userContext)
                },
                onSearchTitle = {
                    searchTitle = it
                    userContext = userContext.copy(searchInput = searchTitle)
                    taskList = taskViewModel.getTaskListForScreen(userContext)
                },
                onClearAllItems = {
                    showRemoveAllAlert = true
                }
            )
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

        if (openAddEditTaskDialog) {
            AddEditTask(
                id = addEditTaskId,
                taskViewModel = taskViewModel,
                onDismiss = { openAddEditTaskDialog = false },
                userContext = userContext,
                onAddEditComplete = {
                    openAddEditTaskDialog = false
                    taskList = taskViewModel.getTaskListForScreen(userContext = userContext)
                }
            )
        }

        if (showRemoveAllAlert) {
            RemoveAllTasks(
                content = stringResource(id = R.string.alert_content_main_screen),
                onConfirmation = {
                    taskViewModel.removeAllTasks(taskList, userContext)
                    showRemoveAllAlert = false
                    taskList = taskViewModel.getTaskListForScreen(userContext)
                },
                onDisMissDialog = { showRemoveAllAlert = false }
            )
        }

        LazyColumn(modifier = Modifier.padding(it)) {

            items(items = taskList,
                key = { item: Task -> item.id }) { task ->
                val dismissState = rememberDismissState(
                    confirmStateChange = { dismissValue ->
                        if (dismissValue == DismissValue.DismissedToEnd
                            || dismissValue == DismissValue.DismissedToStart
                        ) {
                            taskViewModel.removeTask(task, false, userContext)
                            taskList = taskViewModel
                                .getTaskListForScreen(userContext)
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
                                taskViewModel.updateTask(updatedTask, userContext)
                                taskList = taskViewModel
                                    .getTaskListForScreen(userContext)
                            },
                            onMarkComplete = {
                                taskViewModel.removeTask(task, true, userContext)
                                taskList = taskViewModel
                                    .getTaskListForScreen(userContext)
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
    val navController = rememberNavController()
    MainView(taskViewModel, navController = navController)
}