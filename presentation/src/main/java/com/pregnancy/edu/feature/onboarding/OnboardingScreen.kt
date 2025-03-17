package com.pregnancy.edu.feature.onboarding

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.base.permission.Permissions
import com.pregnancy.edu.common.base.permission.rememberPermissionState
import com.pregnancy.edu.feature.onboarding.composable.OnboardingContent
import com.pregnancy.edu.presentation.app.rememberAppState
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

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
    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            permission = Permissions.POST_NOTIFICATIONS,
            onPermissionResult = {
                appState.clearAndNavigate(Destination.Login.route)
            }
        )
    } else null

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
                notificationPermission?.requestPermission()
            },
            pages = onboardingViewModel.uiState.collectAsStateWithLifecycle().value
        )
    }
}