import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.common.theme.Pink100
import com.pregnancy.edu.common.theme.Pink400
import com.pregnancy.edu.common.theme.Pink50
import com.pregnancy.edu.common.theme.Pink500
import com.pregnancy.edu.common.theme.Pink600
import com.pregnancy.edu.common.theme.Pink700
import com.pregnancy.edu.common.theme.Pink800
import com.pregnancy.edu.common.theme.Pink900
import com.pregnancy.edu.feature.home.HomeViewModel
import com.pregnancy.edu.feature.profile.ProfileViewModel
import com.pregnancy.edu.presentation.app.rememberAppState
import com.pregnancy.edu.presentation.navigation.PregnancyAppState
import java.util.Calendar
import java.util.Date

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    HomeScreen(appState = rememberAppState())
}

@Composable
fun HomeScreen(
    appState: PregnancyAppState,
    viewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val profileState = profileViewModel.uiState.collectAsStateWithLifecycle()
    val state = uiState.value
    val user = profileState.value.user

    Column(
        modifier = Modifier
            .background(Pink50)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Pregnancy Progress Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hello, ${user?.fullName?.split(" ")?.firstOrNull() ?: ""}!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Pink800
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${state.trimester} Trimester",
                    style = MaterialTheme.typography.titleMedium,
                    color = Pink600
                )
                Text(
                    text = "Week ${state.weeksPregnant} of 40",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Pink700
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { state.weeksPregnant / 40f },
                    modifier = Modifier.fillMaxWidth(),
                    color = Pink400,
                    trackColor = Pink100
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${state.remainingDays} days until due date",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Pink600
                )
            }
        }

//        // Quick Action Buttons
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            QuickActionButton(
//                icon = Icons.Filled.Lock,
//                text = "Appointments",
//                onClick = { /* Navigate to appointments */ },
//                cardColor = Color.White,
//                iconColor = Pink500,
//                textColor = Pink700
//            )
//            QuickActionButton(
//                icon = Icons.Filled.Lock,
//                text = "Exercises",
//                onClick = { /* Navigate to exercises */ },
//                cardColor = Color.White,
//                iconColor = Pink500,
//                textColor = Pink700
//            )
//        }

        // Health Insights Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "This Week's Insights",
                    style = MaterialTheme.typography.titleMedium,
                    color = Pink800
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.gestationalWeekInsight,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Pink900
                )
            }
        }
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    cardColor: Color,
    iconColor: Color,
    textColor: Color
) {
    ElevatedCard(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp),
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
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
                tint = iconColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = textColor
            )
        }
    }
}