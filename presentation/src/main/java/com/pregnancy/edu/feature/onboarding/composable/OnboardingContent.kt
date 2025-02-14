package com.pregnancy.edu.feature.onboarding.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.composable.PrimaryButton
import com.pregnancy.edu.common.base.composable.Section
import com.pregnancy.edu.feature.onboarding.models.OnboardingPage
import kotlinx.coroutines.launch

@Composable
fun OnboardingContent(
    modifier: Modifier = Modifier,
    onFinishOnboarding: () -> Unit,
    pages: List<OnboardingPage>,
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnboardingPager(
            modifier = Modifier
                .weight(1f),
            pages = pages,
            pagerState = pagerState,
        )
        Section{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Indicators
                val idx = pagerState.currentPage
                OnboardingIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    pageSize = pages.size,
                    currentIdx = idx
                )
                Text(
                    text = pages[idx].title,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(bottom = 64.dp),
                    text = pages[idx].description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                )
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        if (pagerState.currentPage == pages.size - 1) R.string.text_get_started
                        else R.string.text_next
                    ),
                    onClick = {
                        if (pagerState.currentPage == pages.size - 1) {
                            onFinishOnboarding()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                )
            }
        }
    }
}