package com.pregnancy.edu.feature.authentication.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.feature.authentication.otp.composables.OtpVerificationContent
import com.pregnancy.edu.feature.authentication.otp.event.OtpEvent
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

@Composable
fun OtpVerificationScreen(
    email: String,
    appState: PregnancyAppState,
    modifier: Modifier = Modifier,
    viewModel: OtpVerificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onTriggerEvent(OtpEvent.SendOtp(email))
    }

    LaunchedEffect(uiState.otpVerificationSuccess) {
        if (uiState.otpVerificationSuccess) {
            appState.clearAndNavigate(Destination.Login.route)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        OtpVerificationContent(
            onTriggerEvent = viewModel::onTriggerEvent,
            email = email,
            isLoading = uiState.isLoading,
            error = uiState.error
        )
    }
}