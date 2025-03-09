package com.pregnancy.edu.feature.reminder.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Preview
@Composable
fun CalendarStripPreview() {
    CalendarStrip(
        startDate = Calendar.getInstance(),
        daysCount = 7,
        selectedDate = Calendar.getInstance()
    ) {}
}

@Composable
fun CalendarStrip(
    startDate: Calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -3) }, // Start 3 days before today
    daysCount: Int = 7,
    selectedDate: Calendar = Calendar.getInstance(),
    onDateSelected: (Calendar) -> Unit
) {
    // Generate list of calendar days starting from startDate
    val daysList = (0 until daysCount).map { index ->
        (startDate.clone() as Calendar).apply { add(Calendar.DAY_OF_MONTH, index) }
    }

    val dayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(daysList) { calendar ->
            DayItem(
                day = dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1],
                date = calendar.get(Calendar.DAY_OF_MONTH),
                isSelected = isSameDay(calendar, selectedDate),
                onClick = { onDateSelected(calendar.clone() as Calendar) }
            )
        }
    }
}

// Helper function to compare if two Calendar instances represent the same day
private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}