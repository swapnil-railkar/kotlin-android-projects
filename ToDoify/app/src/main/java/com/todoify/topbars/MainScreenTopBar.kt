package com.todoify.topbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.todoify.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MainScreenTopBar(
    onDateChange: (LocalDate) -> Unit,
    onSearchStart: (Boolean) -> Unit
) {

    var openMoreVert by remember { mutableStateOf(false) }
    val moreVertItems = mapOf(
        stringResource(id = R.string.notification_settings) to R.drawable.baseline_settings_24,
        stringResource(id = R.string.history) to R.drawable.baseline_history_24,
        stringResource(id = R.string.clear_all_tasks) to R.drawable.baseline_delete_24
    )
    var selectedMoreVertItem by remember { mutableStateOf("") }

    TopAppBar(
        title = { TopBarContent(onDateChange, onSearchStart) },
        navigationIcon = {
            IconButton(onClick = { openMoreVert = !openMoreVert }) {
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
                        onClick = { selectedMoreVertItem = title }
                    )
                }
            }
        },
        backgroundColor = colorResource(id = R.color.app_default_color),
        contentColor = Color.White
    )

    if (openMoreVert) {
        when(selectedMoreVertItem) {
            stringResource(id = R.string.notification_settings) -> {
                /* Todo open settings pop up */
            }
            stringResource(id = R.string.history) -> {
                /* Todo navigate to history screen */
            }
            stringResource(id = R.string.clear_all_tasks) -> {
                /* Todo delete all tasks */
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarContent(
    onDateChange: (LocalDate) -> Unit,
    onSearchStart: (Boolean) -> Unit
) {
    var searchState by remember { mutableStateOf(false) }
    val calenderState = rememberSheetState()
    var localDateContext by remember { mutableStateOf(LocalDate.now()) }

    CalendarDialog(
        state = calenderState,
        selection = CalendarSelection.Date {
            localDateContext = it
            onDateChange(localDateContext)
        }
    )


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(
                onClick = {
                    calenderState.show()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calender"
                )
            }

            Text(
                text = when (localDateContext) {
                    LocalDate.now() -> stringResource(id = R.string.main_screen)
                    else -> localDateContext.format(DateTimeFormatter.ofPattern("d MMM"))
                }
            )
        }


        IconButton(
            modifier = Modifier.padding(4.dp),
            onClick = {
                searchState = !searchState
                onSearchStart(searchState)
            }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search button")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenTopBarPreview() {
    MainScreenTopBar(onDateChange = {}, onSearchStart = {})
}