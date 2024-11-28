package com.todoify.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.todoify.R

@Composable
fun TopBarMoreVert(
    onClearAllTask: () -> Unit
) {
    var openMoreVert by remember { mutableStateOf(false) }
    val moreVertItems = mutableMapOf<String, Int>(
        stringResource(id = R.string.notification_settings) to R.drawable.baseline_settings_24,
        stringResource(id = R.string.clear_all_tasks) to R.drawable.baseline_delete_24
    )

    var selectedMoreVertItem by remember { mutableStateOf("") }

    IconButton(onClick = { openMoreVert = true }) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more-vert")
    }

    DropdownMenu(expanded = openMoreVert, onDismissRequest = { openMoreVert = false }) {
        moreVertItems.keys.forEach { title ->
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(id = moreVertItems[title]!!),
                            contentDescription = title
                        )
                        Text(text = title, modifier = Modifier.padding(start = 10.dp))
                    }
                },
                onClick = {
                    selectedMoreVertItem = title
                    openMoreVert = false
                }
            )
        }
    }

    when (selectedMoreVertItem) {
        stringResource(id = R.string.notification_settings) -> {
            /* Todo open settings pop up */
        }

        stringResource(id = R.string.clear_all_tasks) -> {
            onClearAllTask()
        }
    }
}