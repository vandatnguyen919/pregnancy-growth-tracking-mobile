package com.pregnancy.edu.feature.authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.feature.authentication.login.LoginScreen
import com.pregnancy.edu.feature.authentication.otp.OtpVerificationScreen
import com.pregnancy.edu.feature.authentication.register.RegisterScreen
import com.pregnancy.edu.presentation.navigation.PregnancyAppState

fun NavGraphBuilder.addAuthenticationNavigation(
    appState: PregnancyAppState
) {
    composable(Destination.Login.route) {
        LoginScreen(appState = appState)
    }
    composable(Destination.Register.route) {
        RegisterScreen(appState = appState)
    }
    composable(
        route ="${Destination.Otp.route}/{email}",
        arguments = listOf(
            navArgument("email") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) { backStackEntry ->
        val email = backStackEntry.arguments?.getString("email") ?: return@composable
        OtpVerificationScreen(email = email, appState = appState)
    }
}