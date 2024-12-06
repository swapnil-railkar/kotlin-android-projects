package com.todoify.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todoify.R

@Composable
fun TaskRemoverSettingsAlert(
    currentSetting: Int,
    onSettingsChanged: (Int?) -> Unit,
    onDisMissAlert: () -> Unit
) {
    var daysInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDisMissAlert() },
        confirmButton = {
            TextButton(onClick = { onSettingsChanged(daysInput.toIntOrNull()) }) {
                Text(text = "Confirm", color = colorResource(id = R.color.app_default_color))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDisMissAlert() }) {
                Text(text = "Cancel", color = colorResource(id = R.color.app_default_color))
            }
        },
        title = { Text(text = "Auto-Delete Task Settings") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(text = "Delete tasks in days :")
                Spacer(modifier = Modifier.height(2.dp))
                TextField(
                    value = daysInput,
                    onValueChange = {
                        daysInput = it
                    },
                    keyboardOptions = KeyboardOptions.Default
                        .copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Current : $currentSetting days")
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun TaskRemoverSettingsAlertPreview() {
    TaskRemoverSettingsAlert(30, {}, {})
}