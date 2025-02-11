package com.pregnancy.edu.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.base.composable.PrimaryButton
import com.pregnancy.edu.common.base.composable.Section
import com.pregnancy.edu.feature.onboarding.composable.OnboardingIndicator
import com.pregnancy.edu.feature.onboarding.composable.OnboardingPager
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(navController = rememberNavController())
}

@Composable
fun OnboardingScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF5F5), // Softer pink at the top
                        Color(0xFFFFCCCC), // More vibrant mid-tone
                        Color(0xFFFF9999)  // Deeper pink at the bottom
                    )
                )
            )
    ) {
        OnboardingContent(
            onFinishOnboarding = {
                navController.navigate(Destination.Login.route) {
                    popUpTo(Destination.Onboarding.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Composable
fun OnboardingContent(
    modifier: Modifier = Modifier,
    onFinishOnboarding: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            title = "Welcome to PregnaJoy",
            description = "Your trusted companion throughout your pregnancy journey",
            imageRes = R.drawable.pregnancy_woman // Replace with your actual image resource
        ),
        OnboardingPage(
            title = "Track Your Progress",
            description = "Monitor your pregnancy week by week with detailed insights",
            imageRes = R.drawable.clock // Replace with your actual image resource
        ),
        OnboardingPage(
            title = "Expert Guidance",
            description = "Access reliable information from healthcare professionals",
            imageRes = R.drawable.baby // Replace with your actual image resource
        )
    )

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
                OnboardingIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    pageSize = pages.size,
                    pagerState = pagerState
                )
                val idx = pagerState.currentPage
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