package com.todoify.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.todoify.R
import com.todoify.commons.Calender
import com.todoify.data.entity.Task
import com.todoify.util.TaskState
import com.todoify.util.UserContext
import com.todoify.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddEditTask(
    id: Long,
    taskViewModel: TaskViewModel,
    userContext: UserContext,
    onDismiss: () -> Unit,
    onAddEditComplete: () -> Unit
) {
    val title: MutableState<String>
    val description: MutableState<String?>
    var completeBy: MutableState<LocalDate?> = remember { mutableStateOf(null) }
    val isDaily: MutableState<Boolean>
    var task: Task? = taskViewModel.getTaskById(id)
    val scrollState = rememberScrollState()
    val calenderState = rememberSheetState()
    var dateText by remember { mutableStateOf("Complete by") }
    val standardModifier = Modifier
        .padding(start = 4.dp, top = 4.dp, end = 4.dp)
        .fillMaxWidth()
    val context = LocalContext.current
    var popUpMessage = "New task added"

    if (task == null) {
        title = remember { mutableStateOf("") }
        description = remember { mutableStateOf(null) }
        completeBy = remember { mutableStateOf(null) }
        isDaily = remember { mutableStateOf(false) }
        dateText = "Complete By"
    } else {
        title = remember { mutableStateOf(task!!.title) }
        description = remember { mutableStateOf(task!!.description) }
        completeBy = if (completeBy.value == null) remember {
            mutableStateOf(task!!.completeBy)
        } else remember {
            mutableStateOf(completeBy.value)
        }
        isDaily = remember { mutableStateOf(task!!.isDaily) }
        popUpMessage = "Task updated successfully"
        dateText =
            if (completeBy.value != null) completeBy.value!!.format(DateTimeFormatter.ofPattern("d MMM")) else dateText

    }

    Calender(
        onDateChange = { date ->
            completeBy.value = date
            dateText = date.format(DateTimeFormatter.ofPattern("d MMM"))
        },
        calenderState = calenderState
    )

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = standardModifier.wrapContentSize(),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = title.value,
                    onValueChange = { input -> title.value = input },
                    label = { Text(text = "Title") },
                    maxLines = 1,
                    modifier = standardModifier
                )

                OutlinedTextField(
                    value = description.value ?: "",
                    onValueChange = { input -> description.value = input },
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
                        checked = isDaily.value,
                        onCheckedChange = { checked -> isDaily.value = checked })
                    Text(text = "Daily Task", modifier = standardModifier)
                }

                Row(
                    modifier = standardModifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            task = createTask(
                                task,
                                taskViewModel.getIdForNewTask(),
                                title.value,
                                description.value,
                                completeBy.value,
                                isDaily.value
                            )
                            val validationErrors: String? = validateTask(task)
                            if (validationErrors != null) {
                                Toast.makeText(context, validationErrors, Toast.LENGTH_LONG).show()
                            } else {
                                if (id == -1L) {
                                    taskViewModel.addTask(task)
                                } else {
                                    taskViewModel.updateTask(task, userContext)
                                }
                                Toast.makeText(context, popUpMessage, Toast.LENGTH_LONG).show()
                                onAddEditComplete()
                            }
                        }
                    ) {
                        Text(
                            text = "Confirm",
                            color = colorResource(id = R.color.app_default_color)
                        )
                    }
                    TextButton(onClick = { onDismiss() }) {
                        Text(
                            text = "Cancel",
                            color = colorResource(id = R.color.app_default_color)
                        )
                    }
                }

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
    val updatedDescription: String? = if (description.isNullOrBlank() || description.isEmpty()) {
        null
    } else {
        description
    }
    return if (task == null) {
        val newTask = Task(
            id = id,
            title = title,
            description = updatedDescription,
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
            description = updatedDescription,
            completeBy = completeBy,
            isDaily = isDaily
        )
        updatedTask
    }
}


private fun validateTask(task: Task?): String? {
    return if (task == null) {
        "Title is empty"
    } else if (task.completeBy != null && task.completeBy < LocalDate.now()) {
        "Invalid complete by date"
    } else {
        null
    }
}
