import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.common.theme.Pink100
import com.pregnancy.edu.common.theme.Pink400
import com.pregnancy.edu.common.theme.Pink50
import com.pregnancy.edu.common.theme.Pink600
import com.pregnancy.edu.common.theme.Pink700
import com.pregnancy.edu.common.theme.Pink800
import com.pregnancy.edu.common.theme.Pink900
import com.pregnancy.edu.feature.home.HomeViewModel
import com.pregnancy.edu.feature.home.composables.GrowthChartsScreen
import com.pregnancy.edu.feature.profile.ProfileViewModel
import com.pregnancy.edu.presentation.navigation.PregnancyAppState
import kotlinx.coroutines.launch

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

    val tabs = state.fetuses
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    // LazyColumn state to track scrolling
    val lazyListState = rememberLazyListState()

    // Check if we should stick the tabs to the top
    val shouldStickTabsToTop by remember {
        derivedStateOf {
            // Stick tabs when scrolled past initial content
            lazyListState.firstVisibleItemIndex > 4
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink50)
    ) {
        // Main content in LazyColumn
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize()
        ) {
            // Top spacing
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Pregnancy Progress Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
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
            }

            // Spacing
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Health Insights Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
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

            // Spacing
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (tabs.isNotEmpty()) {
                // Tab content
                item {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        edgePadding = 0.dp
                    ) {
                        tabs.forEachIndexed { index, fetus ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = { Text(text = fetus.nickName) }
                            )
                        }
                    }
                }

                // Pager content
                item {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) { page ->
                        // Each tab page with scrollable content
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 16.dp)
                        ) {
                            val fetus = state.fetuses[page]
                            GrowthChartsScreen(
                                fetusId = fetus.id,
                                week = state.weeksPregnant
                            )
                        }
                    }
                }
            } else {
                // Show message or placeholder when no tabs/fetuses are available
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No data available",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Pink800
                        )
                    }
                }
            }
        }

        // Sticky header tabs
        if (tabs.isNotEmpty()) {
            ScrollableTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Pink50)
                    .zIndex(if (shouldStickTabsToTop) 1f else -1f)
                    .alpha(if (shouldStickTabsToTop) 1f else 0f),
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp
            ) {
                tabs.forEachIndexed { index, fetus ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = fetus.nickName) }
                    )
                }
            }
        }
    }
}