package com.pregnancy.edu.feature.reminder.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.pregnancy.domain.model.reminder.Reminder
import com.pregnancy.edu.feature.reminder.home.composables.CalendarStrip
import com.pregnancy.edu.feature.reminder.home.composables.ReminderItem
import com.pregnancy.edu.feature.reminder.home.composables.ReminderSearchBox
import com.pregnancy.edu.feature.reminder.home.event.ReminderEvent
import com.pregnancy.edu.presentation.navigation.PregnancyAppState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderScreen(
    modifier: Modifier = Modifier,
    appState: PregnancyAppState,
    viewModel: ReminderViewModel = hiltViewModel()
) {
    val pagingItems: LazyPagingItems<Reminder> = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val context = LocalContext.current

    // State for bottom sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedReminder by remember { mutableStateOf<Reminder?>(null) }
    val bottomSheetState = rememberModalBottomSheetState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5F5))
            .padding(horizontal = 12.dp)
    ) {
        item {
            var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }

            Spacer(modifier = Modifier.height(16.dp))
            CalendarStrip(
                onDateSelected = {
                    selectedDate = it
                    Toast.makeText(context, it.time.toString(), Toast.LENGTH_SHORT).show()
                },
                selectedDate = selectedDate
            )
            IconButton(onClick = { viewModel.onTriggerEvent(ReminderEvent.ReloadReminders) }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reload reminders",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ReminderSearchBox(
                query = "",
                onQueryChange = { },
                onSearchClick = { }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id!! }
        ) { index ->
            pagingItems[index]?.let { reminder ->
                ReminderItem(
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    reminder = reminder,
                    onReminderItemClick = {
                        selectedReminder = reminder
                        showBottomSheet = true
                    }
                )
            }
        }
    }

    // Bottom sheet
    if (showBottomSheet && selectedReminder != null) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            ReminderBottomSheetContent(
                reminder = selectedReminder!!,
                onDeleteClick = {
                    viewModel.onTriggerEvent(ReminderEvent.CancelReminder(selectedReminder!!.id!!))
                    showBottomSheet = false
                },
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}

@Composable
fun ReminderBottomSheetContent(
    reminder: Reminder,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = reminder.title ?: "Reminder",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = reminder.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Delete button
        TextButton(
            onClick = onDeleteClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Reminder",
                    tint = Color.Red
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Delete Reminder",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}