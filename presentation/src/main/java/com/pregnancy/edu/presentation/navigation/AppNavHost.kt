package com.pregnancy.edu.presentation.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.feature.authentication.addAuthenticationNavigation
import com.pregnancy.edu.feature.blogpost.detail.BlogPostDetailScreen
import com.pregnancy.edu.feature.blogpost.home.BlogPostScreen
import com.pregnancy.edu.feature.onboarding.OnboardingScreen
import com.pregnancy.edu.feature.profile.addProfileGraph
import com.pregnancy.edu.feature.reminder.add.AddReminderScreen
import com.pregnancy.edu.feature.reminder.home.ReminderScreen
import com.pregnancy.edu.feature.splashscreen.SplashScreen
import java.util.Calendar

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
                HomeScreen(appState = appState)
            }

            composable(Destination.Blogs.route) {
                BlogPostScreen(appState = appState)
            }

            composable(Destination.Reminder.route) {
                ReminderScreen(appState = appState)
            }

            navigation(
                route = Destination.Profile.route,
                startDestination = Destination.HomeProfile.route
            ) {
                addProfileGraph(appState = appState)
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
            route = Destination.AddReminder.route
        ) {
            AddReminderScreen(appState = appState)
        }
    }
}