import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Date

@Preview
@Composable
fun PregnancyTrackerHomeScreenPreview() {
    val expectedDueDate = Calendar.getInstance().apply {
        set(2024, Calendar.SEPTEMBER, 15)
    }.time

    PregnancyTrackerHomeScreen(expectedDueDate = expectedDueDate)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PregnancyTrackerHomeScreen(
    expectedDueDate: Date,
    motherName: String = "Emma"
) {
    // Calculate pregnancy progress
    val calendar = Calendar.getInstance()
    val currentDate = calendar.time

    // Calculate conception date (40 weeks before due date)
    val conceptionCalendar = Calendar.getInstance().apply {
        time = expectedDueDate
        add(Calendar.WEEK_OF_YEAR, -40)
    }

    // Calculate weeks pregnant
    val millisPerWeek = 7L * 24 * 60 * 60 * 1000
    val weeksPregnant = ((currentDate.time - conceptionCalendar.time.time) / millisPerWeek).toInt()

    // Determine trimester
    val trimester = when {
        weeksPregnant < 13 -> "First"
        weeksPregnant < 27 -> "Second"
        else -> "Third"
    }

    // Calculate remaining days
    val remainingDays = ((expectedDueDate.time - currentDate.time) / (24 * 60 * 60 * 1000)).toInt()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pregnancy Tracker",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Baby") },
                    label = { Text("Baby") },
                    selected = true,
                    onClick = { /* Navigate to baby details */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Mother") },
                    label = { Text("Mother") },
                    selected = false,
                    onClick = { /* Navigate to mother's health */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "Tracking") },
                    label = { Text("Tracking") },
                    selected = false,
                    onClick = { /* Navigate to tracking */ }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pregnancy Progress Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hello, $motherName",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$trimester Trimester",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Week $weeksPregnant of 40",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = weeksPregnant / 40f,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$remainingDays days until due date",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // Quick Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Filled.Lock,
                    text = "Appointments",
                    onClick = { /* Navigate to appointments */ }
                )
                QuickActionButton(
                    icon = Icons.Filled.Lock,
                    text = "Exercises",
                    onClick = { /* Navigate to exercises */ }
                )
            }

            // Health Insights Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "This Week's Insights",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your baby is now about the size of a bell pepper. " +
                                "Focus on staying hydrated and getting enough rest.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}