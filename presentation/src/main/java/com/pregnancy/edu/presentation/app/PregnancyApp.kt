package com.pregnancy.edu.presentation.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.theme.PregnancyTheme
import com.pregnancy.edu.feature.blogpost.detail.composables.BlogPostDetailTopAppBar
import com.pregnancy.edu.presentation.composable.BottomNavigationBar
import com.pregnancy.edu.presentation.navigation.AppNavHost
import com.pregnancy.edu.presentation.navigation.PregnancyAppState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PregnancyApp(
    tokenManager: TokenManager
) {

    PregnancyTheme {
        val appState = rememberAppState()
        val scope = rememberCoroutineScope()
        val navBackStackEntry = appState.navController.currentBackStackEntryAsState()

        // Check authentication status when the app starts
        LaunchedEffect(Unit) {
            scope.launch {
                val isLoggedIn = tokenManager.isLoggedIn()
                if (isLoggedIn) {
                    appState.clearAndNavigate(Destination.Home.route)
                } else {
                    appState.clearAndNavigate(Destination.Onboarding.route)
                }
            }
        }

        val currentRoute = navBackStackEntry.value?.destination?.route ?: ""

        val currentDestination by remember(navBackStackEntry) {
            derivedStateOf {
                navBackStackEntry.value?.destination?.route?.let { route ->
                    Destination.fromString(route)
                } ?: Destination.Splash
            }
        }

        val isShowBottomBar = when (currentDestination.route) {
            Destination.Home.route, Destination.Blogs.route, Destination.Reminder.route, Destination.Profile.route -> true
            else -> false
        }

        val isReminderScreen = currentDestination.route == Destination.Reminder.route

        Scaffold(
            topBar = {
                when {
                    // Show BlogPostDetailTopAppBar when in blog post detail
                    currentRoute.startsWith("${Destination.Blogs.route}/") -> {
                        BlogPostDetailTopAppBar(
                            onBackPressed = { appState.navController.popBackStack() },
                            onShareClick = { /* Implement share functionality */ },
                            onBookmarkClick = { /* Implement bookmark functionality */ }
                        )
                    }

                    currentRoute.startsWith("${Destination.Reminder.route}/") -> {
                        TopAppBar(
                            title = { Text(currentDestination.title) },
                            navigationIcon = {
                                IconButton(onClick = {
                                    appState.navController.popBackStack()
                                }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                                }
                            }
                        )
                    }
                    // Show default top bar only for main bottom nav destinations
                    isShowBottomBar -> {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(currentDestination.title)
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                if (isReminderScreen) {
                    FloatingActionButton(
                        onClick = { appState.navigate(Destination.AddReminder.route) },
                        containerColor = Color(0xFFFAACAA),
                        contentColor = Color.White
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add Reminder"
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Box {
                        AppNavHost(appState = appState)
                    }
                }
            },
            bottomBar = {
                if (!isShowBottomBar) {
                    return@Scaffold
                }
                BottomNavigationBar(
                    currentDestination = currentDestination,
                    onNavigate = { destinationRoute ->
                        appState.navigateToNavigationBar(destinationRoute.route)
                    }
                )
            }
        )
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
): PregnancyAppState {
    return remember(navController) {
        PregnancyAppState(navController)
    }
}