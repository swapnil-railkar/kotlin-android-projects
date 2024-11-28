package com.todoify.commons

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calender(
    onDateChange: (LocalDate) -> Unit,
    calenderState: SheetState
) {
    CalendarDialog(
        state = calenderState,
        selection = CalendarSelection.Date {
            onDateChange(it)
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH
        )
    )
}