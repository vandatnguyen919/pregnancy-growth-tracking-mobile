package com.pregnancy.edu.presentation.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.common.theme.PregnancyTheme
import com.pregnancy.edu.presentation.composable.BottomNavigationBar
import com.pregnancy.edu.presentation.navigation.AppNavHost

@Composable
fun PregnancyApp() {
    PregnancyTheme {

        val navController = rememberNavController()
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination by remember(navBackStackEntry) {
            derivedStateOf {
                navBackStackEntry.value?.destination?.route?.let {
                    Destination.fromString(it)
                } ?: run {
                    Destination.Onboarding
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
                        AppNavHost(
                            navController = navController
                        )
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
                        navController.navigate(destinationRoute.route) {
                            popUpTo(Destination.RootHome.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        )
    }
}