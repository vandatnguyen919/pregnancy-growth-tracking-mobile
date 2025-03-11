package com.pregnancy.edu.feature.profile.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.common.base.Destination
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
    Column(
        modifier = modifier
    ) {
        HomeProfileCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            user = uiState.user ?: return@Column,
            onCardClick = {
                appState.navigate(Destination.ProfileDetail.route)
            }
        )
    }

    Spacer(modifier = Modifier.height(24.dp))
}