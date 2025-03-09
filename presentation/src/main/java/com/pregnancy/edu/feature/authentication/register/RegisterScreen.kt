package com.pregnancy.edu.feature.authentication.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.feature.authentication.login.composable.AuthenticationErrorDialog
import com.pregnancy.edu.feature.authentication.login.event.LoginEvent
import com.pregnancy.edu.feature.authentication.register.composable.RegisterContent
import com.pregnancy.edu.feature.authentication.register.event.RegisterEvent
import com.pregnancy.edu.presentation.app.rememberAppState
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(appState = rememberAppState())
}

@Composable
fun RegisterScreen(
    appState: PregnancyAppState,
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val registerState by registerViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(registerState.isRegisterSuccess) {
        if (registerState.isRegisterSuccess) {
            appState.clearAndNavigate("${Destination.Otp.route}/${registerState.email}")
        }
    }

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
            ),
        contentAlignment = if (registerState.isLoading) Alignment.Center else Alignment.BottomCenter
    ) {
        if (registerState.isLoading) {
            CircularProgressIndicator(
                color = Color.White
            )
        } else {
            RegisterContent(
                registerState = registerState,
                onTriggerEvent = registerViewModel::onTriggerEvent,
                popUpToLogin = {
                    appState.navController.popBackStack(
                        route = Destination.Login.route,
                        inclusive = false
                    )
                }
            )
        }
        registerState.error?.let { error ->
            AuthenticationErrorDialog(
                error = error,
                dismissError = {
                    registerViewModel.onTriggerEvent(RegisterEvent.ErrorDismissed)
                }
            )
        }
    }
}