package com.pregnancy.edu.feature.reminder.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun ReminderScreen(
    modifier: Modifier = Modifier,
    appState: PregnancyAppState,
    viewModel: ReminderViewModel = hiltViewModel()
) {
    val pagingItems: LazyPagingItems<Reminder> = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val context = LocalContext.current

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
                    modifier = Modifier.padding(bottom = 12.dp),
                    reminder = reminder,
                    onReminderItemClick = {
                        Toast.makeText(
                            context,
                            "Clicked on reminder: ${reminder.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}