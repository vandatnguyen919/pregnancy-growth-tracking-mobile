package com.pregnancy.edu.presentation.navigation

import ReminderScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.base.composable.ContentArea
import com.pregnancy.edu.feature.authentication.addAuthenticationNavigation
import com.pregnancy.edu.feature.authentication.login.LoginScreen
import com.pregnancy.edu.feature.authentication.register.RegisterScreen
import com.pregnancy.edu.feature.blogpost.detail.BlogPostDetailScreen
import com.pregnancy.edu.feature.blogpost.home.BlogPostScreen
import com.pregnancy.edu.feature.onboarding.OnboardingScreen
import com.pregnancy.edu.feature.reminder.add.AddReminderScreen
import com.pregnancy.edu.feature.splashscreen.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appState: PregnancyAppState,
    startDestination: String = Destination.Splash.route
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination
    ) {

        // Splash screen
        composable(Destination.Splash.route) {
            SplashScreen()
        }

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
            addAuthenticationNavigation(appState = appState)
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
                BlogPostScreen(appState = appState)
            }

            composable(Destination.Reminder.route) {
                ReminderScreen(appState = appState)
            }

            composable(Destination.Profile.route) {
                ContentArea(
                    modifier = Modifier.fillMaxSize(),
                    destination = Destination.Profile
                )
            }
        }

        composable(
            route = "${Destination.Blogs.route}/{blogPostId}",
            arguments = listOf(
                navArgument("blogPostId") { type = NavType.StringType },
            )
        ) { navBackStackEntry ->
            val blogPostId = navBackStackEntry.arguments?.getString("blogPostId") ?: return@composable
            BlogPostDetailScreen(
                appState = appState,
                blogPostId = blogPostId.toLong()
            )
        }

        composable(
            route = "test"
        ) {
            AddReminderScreen()
        }
    }
}