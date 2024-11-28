package com.example.musicappui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicappui.navigation.Screens

@Composable
fun DrawerItem(
    selected: Boolean,
    onDrawerItemClicked: () -> Unit,
    item: Screens.DrawerScreen
) {
    val backgroundColor = if (selected) Color.LightGray else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .clickable { onDrawerItemClicked() }
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            modifier = Modifier.padding(end = 8.dp, top = 4.dp)
        )
        
        Text(text = item.title, style = MaterialTheme.typography.h5)
    }
}