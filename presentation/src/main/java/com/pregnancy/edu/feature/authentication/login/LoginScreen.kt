package com.pregnancy.edu.feature.authentication.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.feature.authentication.login.composable.AuthenticationErrorDialog
import com.pregnancy.edu.feature.authentication.login.composable.LoginContent
import com.pregnancy.edu.feature.authentication.login.event.LoginEvent
import com.pregnancy.edu.presentation.app.rememberAppState
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(appState = rememberAppState())
}

@Composable
fun LoginScreen(
    appState: PregnancyAppState,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by loginViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(loginState.isAuthenticated) {
        if (loginState.isAuthenticated) {
            appState.clearAndNavigate(Destination.Home.route)
        }
    }

    Column(
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
        // Logo at the top with padding
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(250.dp),
                painter = painterResource(id = R.drawable.pregna_joy),
                contentDescription = stringResource(R.string.app_name)
            )
            if (loginState.isLoading) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }
        // Login box
        AnimatedVisibility(
            visible = !loginState.isLoading
        ) {
            LoginContent(
                loginState = loginState,
                onTriggerEvent = loginViewModel::onTriggerEvent,
                onNavigateToRegister = {
                    appState.navigate(Destination.Register.route)
                }
            )
        }
        loginState.error?.let { error ->
            AuthenticationErrorDialog(
                error = error,
                dismissError = {
                    loginViewModel.onTriggerEvent(LoginEvent.ErrorDismissed)
                }
            )
        }
    }
}