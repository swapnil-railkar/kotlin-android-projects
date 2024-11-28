package com.example.musicappui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicappui.constants.Constants
import com.example.musicappui.constants.LibraryItem

@Composable
fun Library() {
    LazyColumn{
        items(Constants().libraryItems) {
            item -> LibraryItemView(libraryItem = item)
        }
    }
}

@Composable
private fun LibraryItemView(libraryItem: LibraryItem) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    painter = painterResource(id = libraryItem.drawable),
                    contentDescription = libraryItem.title,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(text = libraryItem.title)
            }
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow right")
        }
        Divider(color = Color.DarkGray)
    }
}
