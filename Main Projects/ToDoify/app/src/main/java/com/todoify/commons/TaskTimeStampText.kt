package com.todoify.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todoify.data.entity.Task
import com.todoify.navigation.Screens
import com.todoify.util.TaskState
import com.todoify.util.typeconverter.LocalDateTypeConverter
import com.todoify.util.typeconverter.TaskStateTypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TaskTimeStamps(
    task: Task,
    screenContext: String
) {
    val startString = if (screenContext == Screens.MainScreen.route) "From : " else "Created At : "
    val endString = if (screenContext == Screens.MainScreen.route) {
        "To : "
    } else if (TaskStateTypeConverter.toTaskState(task.status) == TaskState.COMPLETED) {
        "Completed at : "
    } else {
        "Removed At : "
    }

    val startTimeStamp =
        startString + getFormattedDate(LocalDateTypeConverter.toLocalDate(task.createdAt))
    val endTimeStamp = if (screenContext == Screens.HistoryScreen.route) {
        endString + getFormattedDate(LocalDateTypeConverter.toLocalDate(task.removedAt))
    } else {
        endString + getFormattedDate(LocalDateTypeConverter.toLocalDate(task.completeBy))
    }


    Column {
        Text(
            text = task.title, overflow = TextOverflow.Ellipsis,
            maxLines = 1, modifier = Modifier.width(275.dp)
        )
        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = startTimeStamp,
                color = Color.DarkGray, fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = endTimeStamp,
                color = Color.DarkGray, fontSize = 12.sp
            )

        }
    }
}

private fun getFormattedDate(date: LocalDate?): String {
    return if (date != null) {
        date.format(DateTimeFormatter.ofPattern("d MMM"))
    } else {
        ""
    }
}