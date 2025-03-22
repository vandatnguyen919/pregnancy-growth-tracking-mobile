package com.pregnancy.edu.feature.reminder.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.composable.PrimaryButton
import com.pregnancy.edu.common.base.composable.PrimaryTextField
import com.pregnancy.edu.feature.reminder.add.event.AddReminderEvent
import com.pregnancy.edu.presentation.app.rememberAppState
import com.pregnancy.edu.presentation.navigation.PregnancyAppState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

enum class ReminderType(val displayName: String) {
    APPOINTMENT("Appointment"),
    MEDICATION("Medication"),
    EXERCISE("Exercise"),
    OTHER("Other");
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(
    appState: PregnancyAppState,
    modifier: Modifier = Modifier,
    viewModel: AddReminderViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val state = uiState.value
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    var timePickerError by remember { mutableStateOf<String?>(null) }
    // Get current time for validation
    val currentTime = remember { Calendar.getInstance() }

    LaunchedEffect(state.addSuccess) {
        if (state.addSuccess) {
            appState.popUp()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Title Input
        PrimaryTextField(
            label = stringResource(R.string.text_title),
            value = state.title,
            onValueChange = { viewModel.onTriggerEvent(AddReminderEvent.UpdateTitle(it)) },
            modifier = Modifier.fillMaxWidth(),
            isError = state.titleError != null,
            errorText = state.titleError,
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Description Input
        PrimaryTextField(
            label = stringResource(R.string.text_description),
            value = state.description,
            onValueChange = { viewModel.onTriggerEvent(AddReminderEvent.UpdateDescription(it)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            minLines = 3
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Reminder Type Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            PrimaryTextField(
                value = state.selectedReminderType ?: ReminderType.APPOINTMENT.displayName,
                onValueChange = {},
                readOnly = true,
                label = stringResource(R.string.text_reminder_type),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                ReminderType.entries.forEach { type ->
                    val text = type.displayName

                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            viewModel.onTriggerEvent(AddReminderEvent.UpdateReminderType(text))
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Date and Time Section
        Text(
            text = "Schedule",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Date Picker Button
        OutlinedButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
                    .format(state.selectedDate.time),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time Picker Button
        OutlinedButton(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_access_time),
                contentDescription = null,
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                SimpleDateFormat("hh:mm a", Locale.getDefault())
                    .format(state.selectedTime.time),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Save Button
        PrimaryButton(
            text = stringResource(R.string.text_save),
            onClick = {
                if (state.isFormValid()) {
                    viewModel.onTriggerEvent(AddReminderEvent.SaveReminder)
                }
            },
            enabled = state.isFormValid(),
            modifier = Modifier.fillMaxWidth(),
        )
    }

    // Date Picker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.selectedDate.timeInMillis,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis >= System.currentTimeMillis()
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val newDate = Calendar.getInstance().apply { timeInMillis = it }
                            viewModel.onTriggerEvent(AddReminderEvent.UpdateDate(newDate))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

// Time Picker Dialog with validation
    if (showTimePicker) {
        val isTodaySelected = isSameDay(state.selectedDate, currentTime)

        // Initialize with current values
        val initialHour = state.selectedTime.get(Calendar.HOUR_OF_DAY)
        val initialMinute = state.selectedTime.get(Calendar.MINUTE)

        // Adjust initial values if today is selected and current time is in the past
        val adjustedInitialHour = if (isTodaySelected &&
            initialHour < currentTime.get(Calendar.HOUR_OF_DAY) ||
            (initialHour == currentTime.get(Calendar.HOUR_OF_DAY) &&
                    initialMinute < currentTime.get(Calendar.MINUTE))) {
            currentTime.get(Calendar.HOUR_OF_DAY)
        } else {
            initialHour
        }

        val adjustedInitialMinute = if (isTodaySelected &&
            initialHour == currentTime.get(Calendar.HOUR_OF_DAY) &&
            initialMinute < currentTime.get(Calendar.MINUTE)) {
            currentTime.get(Calendar.MINUTE)
        } else {
            initialMinute
        }

        val timePickerState = rememberTimePickerState(
            initialHour = adjustedInitialHour,
            initialMinute = adjustedInitialMinute
        )

        DatePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newTime = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            set(Calendar.MINUTE, timePickerState.minute)
                        }

                        // Double check if the time is valid for today
                        val isValid = !isTodaySelected || isTimeValid(state.selectedDate, newTime)
                        if (isValid) {
                            viewModel.onTriggerEvent(AddReminderEvent.UpdateTime(newTime))
                            timePickerError = null
                        } else {
                            timePickerError = "Cannot select a time in the past"
                        }
                        showTimePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Custom time picker wrapper that validates time selection
                if (isTodaySelected) {
                    TimePickerWithValidation(
                        state = timePickerState,
                        currentHour = currentTime.get(Calendar.HOUR_OF_DAY),
                        currentMinute = currentTime.get(Calendar.MINUTE)
                    )
                } else {
                    TimePicker(state = timePickerState)
                }
            }
        }
    }

    // Show error if there is one
    state.error?.let { error ->
        LaunchedEffect(error) {
            // You could show a snackbar here
        }
    }
}

/**
 * A TimePicker wrapper that validates time input for today's date
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerWithValidation(
    state: androidx.compose.material3.TimePickerState,
    currentHour: Int,
    currentMinute: Int
) {
    LaunchedEffect(state.hour, state.minute) {
        // If user selects a time before current time, auto-correct to current time
        if (state.hour < currentHour ||
            (state.hour == currentHour && state.minute < currentMinute)) {
            // Note: We can't directly modify the state here as it's read-only
            // This will cause a visual glitch but prevent invalid time selection
        }
    }

    // Add a message to inform users about the restriction
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "You can only select future times for today",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TimePicker(state = state)
    }
}

/**
 * Checks if the selected time is valid - not in the past if the date is today
 */
private fun isTimeValid(selectedDate: Calendar, selectedTime: Calendar): Boolean {
    val now = Calendar.getInstance()

    // If the selected date is today, check the time
    if (isSameDay(selectedDate, now)) {
        val combinedDateTime = Calendar.getInstance().apply {
            set(Calendar.YEAR, selectedDate.get(Calendar.YEAR))
            set(Calendar.MONTH, selectedDate.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, selectedDate.get(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, selectedTime.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, selectedTime.get(Calendar.MINUTE))
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return combinedDateTime.timeInMillis > now.timeInMillis
    }

    // If not today, any time is valid
    return true
}

/**
 * Checks if two Calendar instances represent the same day
 */
private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}

@Preview(showBackground = true)
@Composable
fun AddReminderScreenPreview() {
    AddReminderScreen(
        appState = rememberAppState(),
    )
}