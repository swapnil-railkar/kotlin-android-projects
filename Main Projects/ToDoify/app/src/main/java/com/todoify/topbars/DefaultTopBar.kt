package com.todoify.topbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.todoify.R
import com.todoify.commons.Calender
import com.todoify.commons.TopBarMoreVert
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DefaultTopBar(
    onDateChange: (LocalDate) -> Unit,
    onSearchStateChange: (Boolean) -> Unit,
    onClearAllItems: () -> Unit,
    onOpenSettings: () -> Unit
) {
    var searchState by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TopBarContent(onDateChange = onDateChange)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        searchState = !searchState
                        onSearchStateChange(searchState)
                    }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search button"
                    )
                }
            }
        },
        navigationIcon = {
            TopBarMoreVert(
                onClearAllTask = { onClearAllItems() },
                onOpenSettingsAlert = { onOpenSettings() }
            )
        },
        backgroundColor = colorResource(id = R.color.app_default_color)
    )
}

@Composable
private fun TopBarContent(
    onDateChange: (LocalDate) -> Unit,
) {
    var localDateContext by remember { mutableStateOf(LocalDate.now()) }
    val calenderState = rememberSheetState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
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

    }

    Calender(
        onDateChange = {
            localDateContext = it
            onDateChange(localDateContext)
        },
        calenderState = calenderState
    )


}