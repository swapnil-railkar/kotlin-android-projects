package com.todoify.commons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.todoify.R

@Composable
fun SearchField(
    onSearchTitle: (String) -> Unit
) {
    var searchTitle by remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchTitle,
        onValueChange = { input ->
            searchTitle = input
            onSearchTitle(searchTitle)
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
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear"
                )
            }
        }
    )
}