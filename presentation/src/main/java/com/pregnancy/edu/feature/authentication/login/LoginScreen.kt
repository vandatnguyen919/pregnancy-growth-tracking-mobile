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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pregnancy.edu.R
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.feature.authentication.login.composable.LoginContent

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val loginState by loginViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(loginState.isAuthenticated) {
        if (loginState.isAuthenticated) {
            navController.navigate(Destination.Home.route) {
                popUpTo(Destination.Login.route) { inclusive = true }
            }
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
                navController = navController,
                loginState = loginState,
                onTriggerEvent = loginViewModel::onTriggerEvent
            )
        }
    }
}