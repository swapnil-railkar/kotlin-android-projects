package com.todoify.topbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.todoify.commons.DefaultTopBar
import java.time.LocalDate
import com.todoify.R

@Composable
fun HistoryScreenTopBar(
    onDateChange: (LocalDate) -> Unit,
    onSearchStart: (Boolean) -> Unit,
    onClearAllItems: () -> Unit
) {
    DefaultTopBar(
        topBarContent = {
            HistoryTopBarContent(
                onDateChange = onDateChange,
                onSearchStart = onSearchStart
            )
        },
        onClearAllItems = onClearAllItems
    )
}

@Composable
private fun HistoryTopBarContent(
    onDateChange: (LocalDate) -> Unit,
    onSearchStart: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.history), fontWeight = FontWeight.ExtraBold)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentSize()
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HistoryScreenTopBarPreview() {
    HistoryScreenTopBar(onDateChange = {}, onSearchStart = {}, onClearAllItems = {})
}