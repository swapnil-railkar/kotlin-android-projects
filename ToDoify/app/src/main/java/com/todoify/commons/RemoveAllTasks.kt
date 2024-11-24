package com.todoify.commons

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.todoify.R

@Composable
fun RemoveAllTasks(
    content: String,
    onConfirmation: () -> Unit,
    onDisMissDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDisMissDialog() },
        confirmButton = { 
            TextButton(onClick = { onConfirmation() }) {
                Text(text = "Confirm", color = colorResource(id = R.color.app_default_color))
            }
        },
        title = { Text(text = stringResource(id = R.string.remove_all_tasks))},
        text = { Text(text = content)}
    )
}