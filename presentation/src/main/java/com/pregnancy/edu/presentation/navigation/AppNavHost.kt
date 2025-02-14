package com.pregnancy.edu.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.base.composable.ContentArea
import com.pregnancy.edu.feature.authentication.login.LoginScreen
import com.pregnancy.edu.feature.onboarding.OnboardingScreen
import com.pregnancy.edu.feature.authentication.register.RegisterScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appState: PregnancyAppState,
    startDestination: String = Destination.RootOnboarding.route
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination
    ) {
        // Onboarding
        navigation(
            route = Destination.RootOnboarding.route,
            startDestination = Destination.Onboarding.route
        ) {
            composable(Destination.Onboarding.route) {
                OnboardingScreen(appState = appState)
            }
        }

        // Authentication
        navigation(
            route = Destination.RootAuth.route,
            startDestination = Destination.Login.route
        ) {
            composable(Destination.Login.route) {
                LoginScreen(appState = appState)
            }
            composable(Destination.Register.route) {
                RegisterScreen(appState = appState)
            }
        }

        navigation(
            route = Destination.RootHome.route,
            startDestination = Destination.Home.route
        ) {
            composable(Destination.Home.route) {
                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destination = Destination.Home
                )
            }

            composable(Destination.Blogs.route) {
                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destination = Destination.Blogs
                )
            }

            composable(Destination.Reminder.route) {
                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destination = Destination.Reminder
                )
            }

            composable(Destination.Profile.route) {
                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destination = Destination.Profile
                )
            }
        }
    }
}