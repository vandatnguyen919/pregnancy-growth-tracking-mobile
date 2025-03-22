package com.pregnancy.edu.feature.profile.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.theme.Pink50
import com.pregnancy.edu.feature.profile.ProfileViewModel
import com.pregnancy.edu.feature.profile.home.composables.HomeProfileCard
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

@Composable
fun HomeProfileScreen(
    appState: PregnancyAppState,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Pink50),
        contentAlignment = Alignment.TopCenter
    ) {
        HomeProfileCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(top = 16.dp),
            user = uiState.user ?: return@Box,
            onCardClick = {
                appState.navigate(Destination.ProfileDetail.route)
            }
        )
    }
}