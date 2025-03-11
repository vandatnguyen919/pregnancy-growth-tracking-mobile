package com.pregnancy.edu.feature.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.feature.profile.detail.ProfileDetailScreen
import com.pregnancy.edu.feature.profile.home.HomeProfileScreen
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

fun NavGraphBuilder.addProfileGraph(
    appState: PregnancyAppState
) {
    // Home profile
    composable(Destination.HomeProfile.route) {
        HomeProfileScreen(appState = appState)
    }

    // Profile detail
    composable(Destination.ProfileDetail.route) {
        ProfileDetailScreen(appState = appState)
    }
}