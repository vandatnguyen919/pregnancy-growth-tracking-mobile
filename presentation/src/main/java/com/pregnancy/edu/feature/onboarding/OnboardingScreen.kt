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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.base.composable.PrimaryButton
import com.pregnancy.edu.common.base.composable.Section
import com.pregnancy.edu.feature.onboarding.composable.OnboardingContent
import com.pregnancy.edu.feature.onboarding.composable.OnboardingIndicator
import com.pregnancy.edu.feature.onboarding.composable.OnboardingPager
import com.pregnancy.edu.presentation.app.rememberAppState
import com.pregnancy.edu.presentation.navigation.PregnancyAppState
import kotlinx.coroutines.launch

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(appState = rememberAppState())
}

@Composable
fun OnboardingScreen(
    appState: PregnancyAppState,
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel = viewModel()
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
                appState.clearAndNavigate(Destination.Login.route)
            },
            pages = onboardingViewModel.uiState.collectAsStateWithLifecycle().value
        )
    }
}