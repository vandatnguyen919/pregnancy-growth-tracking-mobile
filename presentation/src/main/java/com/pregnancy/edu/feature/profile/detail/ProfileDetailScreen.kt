package com.pregnancy.edu.feature.profile.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.feature.profile.ProfileViewModel
import com.pregnancy.edu.feature.profile.detail.composables.HomeProfileContent
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

@Composable
fun ProfileDetailScreen(
    appState: PregnancyAppState,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFFFF0F3)), // Lightest pink as background
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFFFF4D6D) // Medium pink for progress indicator
            )
        }
    } else {
        HomeProfileContent(
            user = uiState.user ?: return,
            modifier = modifier.background(Color(0xFFFFF0F3)) // Lightest pink background
        )
    }
}