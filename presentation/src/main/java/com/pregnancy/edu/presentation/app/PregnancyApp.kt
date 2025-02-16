package com.pregnancy.edu.presentation.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.theme.PregnancyTheme
import com.pregnancy.edu.presentation.composable.BottomNavigationBar
import com.pregnancy.edu.presentation.navigation.AppNavHost
import com.pregnancy.edu.presentation.navigation.PregnancyAppState
import kotlinx.coroutines.launch

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

        val currentDestination by remember(navBackStackEntry) {
            derivedStateOf {
                navBackStackEntry.value?.destination?.route?.let {
                    Destination.fromString(it)
                } ?: run {
                    Destination.Splash
                }
            }
        }
        val isShowBottomBar = when (currentDestination.route) {
            Destination.Home.route, Destination.Blogs.route, Destination.Reminder.route, Destination.Profile.route -> true
            else -> false
        }

        Scaffold(
            topBar = { },
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
                        appState.navigate(destinationRoute.route)
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