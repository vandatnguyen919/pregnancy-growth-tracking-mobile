package com.pregnancy.edu.feature.authentication.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.feature.authentication.register.composable.RegisterContent
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
    registerViewModel: RegisterViewModel = viewModel()
) {
    val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()
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
        contentAlignment = Alignment.BottomCenter
    ) {
        RegisterContent(
            registerState = uiState,
            onTriggerEvent = registerViewModel::onTriggerEvent,
            popUpToLogin = {
                appState.navController.popBackStack(
                    route = Destination.Login.route,
                    inclusive = false
                )
            }
        )
    }
}