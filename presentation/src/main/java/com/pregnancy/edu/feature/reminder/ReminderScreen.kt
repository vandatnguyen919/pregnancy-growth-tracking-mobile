import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pregnancy.domain.model.reminder.Reminder
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.composable.PrimaryTextField
import com.pregnancy.edu.feature.reminder.composables.CalendarStrip
import com.pregnancy.edu.feature.reminder.composables.ReminderItem
import com.pregnancy.edu.feature.reminder.composables.ReminderSearchBox
import com.pregnancy.edu.presentation.app.rememberAppState
import com.pregnancy.edu.presentation.navigation.PregnancyAppState
import java.util.*

data class ReminderTime(
    val label: String,
    val color: Color = Color(0xFFE6E1FD)
)

@Preview
@Composable
fun ReminderScreenPreview() {
    ReminderScreen(appState = rememberAppState())
}

@Composable
fun ReminderScreen(
    modifier: Modifier = Modifier,
    appState: PregnancyAppState
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color(0xFFFFF5F5))
            .padding(16.dp)
    ) {
        var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }

        val context = LocalContext.current
        Spacer(modifier = Modifier.height(16.dp))
        CalendarStrip(
            onDateSelected = {
                selectedDate = it
                Toast.makeText(context, it.time.toString(), Toast.LENGTH_SHORT).show()
            },
            selectedDate = selectedDate
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReminderSearchBox(
            query = "",
            onQueryChange = { },
            onSearchClick = { }
        )
        Spacer(modifier = Modifier.height(24.dp))
        RemindersList()
    }
}

@Composable
fun RemindersList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.cd_today),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        ReminderItem(
            reminder = Reminder(
                id = 1,
                description = "Meeting with Dr. Smith",
                reminderDate = "2025-02-23",
                reminderType = "Appointment",
            )
        ) { }
        ReminderItem(
            reminder = Reminder(
                id = 1,
                description = "Meeting with Dr. Smith",
                reminderDate = "2025-02-23",
                reminderType = "Medicine",
            )
        ) { }
        Text(
            text = stringResource(id = R.string.cd_tomorrow),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        ReminderItem(
            reminder = Reminder(
                id = 1,
                description = "Meeting with Dr. Smith",
                reminderDate = "2025-02-23",
                reminderType = "Checkup",
            )
        ) { }
        ReminderItem(
            reminder = Reminder(
                id = 1,
                description = "Meeting with Dr. Smith",
                reminderDate = "2025-02-23",
                reminderType = "Appointment",
            )
        ) { }
        ReminderItem(
            reminder = Reminder(
                id = 1,
                description = "Meeting with Dr. Smith",
                reminderDate = "2025-02-23",
                reminderType = "Appointment",
            )
        ) { }
    }
}