package com.todoify.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.todoify.data.Task
import com.todoify.topbars.MainScreenTopBar
import com.todoify.util.TaskState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.todoify.R
import com.todoify.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(
    taskViewModel: TaskViewModel
) {
    var dateContext by remember { mutableStateOf(LocalDate.now()) }
    var searchState by remember { mutableStateOf(false) }
    var searchTitle by remember { mutableStateOf("") }
    var taskList by remember { mutableStateOf(taskViewModel
        .getTaskListForMainScreen(dateContext, searchTitle)) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                MainScreenTopBar(
                    onDateChange = {
                        dateContext = it
                        taskList = taskViewModel.getTaskListForMainScreen(dateContext, searchTitle)
                    },
                    onSearchStart = {
                        searchState = it
                    }
                )

                if (searchState) {
                    OutlinedTextField(
                        value = searchTitle,
                        onValueChange = {
                            input ->
                            searchTitle = input
                            taskList = taskViewModel.getTaskListForMainScreen(dateContext, searchTitle)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        maxLines = 1,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = colorResource(id = R.color.app_default_color)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { searchTitle = "" }) {
                                Icon(imageVector = Icons.Default.Clear,
                                    contentDescription = "clear")
                            }
                        }
                    )
                }
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*Todo navigate to add/edit screen as add op*/ },
                backgroundColor = colorResource(id = R.color.app_default_color)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new task")
            }
        }
    ) {
        if (searchState) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                TextField(value = searchTitle, onValueChange = { input -> searchTitle = input })
            }
        }
        
        LazyColumn(modifier = Modifier.padding(it)) {

            items(items = taskList,
                key = {item: Task ->  item.id}) {
                task ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        dismissValue ->
                        if (dismissValue == DismissValue.DismissedToEnd
                            || dismissValue == DismissValue.DismissedToStart) {
                            taskViewModel.deleteTask(task, false)
                            taskList = taskViewModel
                                .getTaskListForMainScreen(dateContext, searchTitle)
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background = {},
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(0.25f)},
                    dismissContent = {
                        TaskItem(
                            task = task,
                            onMarkImportant = {
                                important ->
                                val updatedTask = task.copy(isImportant = important)
                                taskViewModel.updateTask(updatedTask)
                                taskList = taskViewModel
                                    .getTaskListForMainScreen(dateContext, searchTitle)
                            },
                            onMarkComplete = {
                                taskViewModel.deleteTask(task, true)
                                taskList = taskViewModel
                                    .getTaskListForMainScreen(dateContext, searchTitle)
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
    onMarkComplete: () -> Unit
) {
    var starColor by remember{ mutableStateOf(Color.LightGray) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable { /* Todo Navigate to add/edit screen */ },
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

            Column {
                Text(text = task.title, overflow = TextOverflow.Ellipsis,
                    maxLines = 1, modifier = Modifier.width(275.dp))
                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(text = "From : ${getFormattedDate(task.createdAt)}",
                        color = Color.DarkGray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    if (task.completeBy != null) {
                        Text(text = "To : ${getFormattedDate(task.completeBy)}",
                            color = Color.DarkGray, fontSize = 12.sp)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                starColor = if(task.isImportant) Color.Yellow else Color.LightGray
                IconButton(onClick = { onMarkImportant(!task.isImportant) }) {
                    Icon(imageVector = Icons.Default.Star,
                        contentDescription = "mark important", tint = starColor)
                }
                IconButton(onClick = { onMarkComplete() }) {
                    Icon(imageVector = Icons.Default.Check,
                        contentDescription = "mark as complete", tint = Color.Green)
                }
            }

        }
    }
}

private fun getFormattedDate(date: LocalDate?): String {
    return if (date == null) {
        ""
    } else {
        date.format(DateTimeFormatter.ofPattern("d MMM"))
    }
}


@Composable
@Preview(showBackground = true)
fun MainViewPreview() {
    val taskViewModel: TaskViewModel = viewModel()
    MainView(taskViewModel)
}

@Composable
@Preview(showBackground = true)
fun TaskItemPreview() {
    val taskItem = Task(
        title = "Dummy Test",
        description = null,
        createdAt = LocalDate.now(),
        completeBy = LocalDate.now().plusDays(3),
        isImportant = false,
        isDaily = false,
        status = TaskState.IN_PROGRESS,
        removedAt = null
    )
    TaskItem(
        task = taskItem,
        onMarkImportant = {},
        onMarkComplete = {}
    )
}