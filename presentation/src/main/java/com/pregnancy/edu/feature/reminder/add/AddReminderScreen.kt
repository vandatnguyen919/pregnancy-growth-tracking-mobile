package com.pregnancy.edu.feature.reminder.add

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.R
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Title Input
        OutlinedTextField(
            value = state.title,
            onValueChange = { viewModel.onTriggerEvent(AddReminderEvent.UpdateTitle(it)) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = state.titleError != null,
            supportingText = {
                if (state.titleError != null) {
                    Text(
                        text = state.titleError,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Description Input
        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.onTriggerEvent(AddReminderEvent.UpdateDescription(it)) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            minLines = 3
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Reminder Type Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.selectedReminderType ?: ReminderType.APPOINTMENT.displayName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Reminder Type") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
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
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.DateRange, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
                    .format(state.selectedDate.time)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time Picker Button
        OutlinedButton(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(painter = painterResource(R.drawable.ic_access_time), contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                SimpleDateFormat("hh:mm a", Locale.getDefault())
                    .format(state.selectedTime.time)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Save Button
        Button(
            onClick = {
                if (state.isFormValid()) {
                    viewModel.onTriggerEvent(AddReminderEvent.SaveReminder)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Save Reminder")
        }
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

    // Time Picker Dialog
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = state.selectedTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = state.selectedTime.get(Calendar.MINUTE)
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
                        viewModel.onTriggerEvent(AddReminderEvent.UpdateTime(newTime))
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
                TimePicker(state = timePickerState)
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

@Preview(showBackground = true)
@Composable
fun AddReminderScreenPreview() {
    AddReminderScreen(
        appState = rememberAppState(),
    )
}