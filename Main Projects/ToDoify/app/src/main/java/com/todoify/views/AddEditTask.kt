package com.todoify.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.todoify.R
import com.todoify.commons.Calender
import com.todoify.data.Task
import com.todoify.navigation.Screens
import com.todoify.util.TaskState
import com.todoify.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddEditTask(
    id: Long,
    taskViewModel: TaskViewModel,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var description: String? by remember { mutableStateOf(null) }
    var completeBy: LocalDate? by remember { mutableStateOf(null) }
    var isDaily by remember { mutableStateOf(false) }
    var task: Task? = taskViewModel.getTaskById(id)
    val scrollState = rememberScrollState()
    val calenderState = rememberSheetState()
    var dateText by remember { mutableStateOf("Complete by") }
    val standardModifier = Modifier
        .padding(start = 4.dp, top = 4.dp, end = 4.dp)
        .fillMaxWidth()
    val context = LocalContext.current
    var headerText by remember { mutableStateOf(title) }
    var popUpMessage = "New task added"

    if (task != null) {
        title = task.title
        description = task.description ?: ""
        completeBy = task.completeBy
        isDaily = task.isDaily
        headerText = title
        popUpMessage = "Task updated successfully"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = headerText.ifEmpty { "Add Task" },
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.width(200.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screens.MainScreen.route) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                backgroundColor = colorResource(id = R.color.app_default_color)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    task = createTask(
                        task,
                        taskViewModel.getIdForNewTask(),
                        title,
                        description,
                        completeBy
                    )
                    if (task == null) {
                        Toast.makeText(context, "Title is empty", Toast.LENGTH_LONG).show()
                    } else {
                        taskViewModel.addTask(task)
                        Toast.makeText(context, popUpMessage, Toast.LENGTH_LONG).show()
                        navController.navigate(Screens.MainScreen.route)
                    }
                },
                containerColor = colorResource(id = R.color.app_default_color)
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Add task")
            }
        }
    ) {

        Calender(
            onDateChange = { date ->
                completeBy = date
                dateText = date.format(DateTimeFormatter.ofPattern("d MMM"))
            },
            calenderState = calenderState
        )


        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { input -> title = input },
                label = { Text(text = "Title") },
                maxLines = 1,
                modifier = standardModifier
            )

            OutlinedTextField(
                value = description ?: "",
                onValueChange = { input -> description = input },
                label = { Text(text = "Description") },
                modifier = standardModifier
                    .height(300.dp)
                    .verticalScroll(scrollState)
            )

            Row(
                modifier = standardModifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        calenderState.show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Complete by"
                    )
                }

                Text(text = dateText, modifier = standardModifier)
            }

            Row(
                modifier = standardModifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isDaily,
                    onCheckedChange = { checked -> isDaily = checked })
                Text(text = "Daily Task", modifier = standardModifier)
            }

        }
    }
}

private fun createTask(
    task: Task?,
    id: Long,
    title: String,
    description: String?,
    completeBy: LocalDate?,
    isDaily: Boolean = false
): Task? {
    if (title.isEmpty() || title.isBlank()) {
        return null
    }
    return if (task == null) {
        val newTask = Task(
            id = id,
            title = title,
            description = description,
            createdAt = LocalDate.now(),
            completeBy = completeBy,
            removedAt = null,
            isImportant = false,
            isDaily = isDaily,
            status = TaskState.IN_PROGRESS
        )
        newTask
    } else {
        val updatedTask = task.copy(
            title = title,
            description = description,
            completeBy = completeBy,
            isDaily = isDaily
        )
        updatedTask
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditTaskPreview() {
    val taskViewModel: TaskViewModel = viewModel()
    val navController = rememberNavController()
    AddEditTask(id = -1L, taskViewModel = taskViewModel, navController)
}
