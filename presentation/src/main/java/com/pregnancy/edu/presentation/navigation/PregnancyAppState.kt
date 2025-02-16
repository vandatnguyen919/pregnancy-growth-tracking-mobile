package com.pregnancy.edu.presentation.navigation

import androidx.navigation.NavHostController
import com.pregnancy.edu.common.base.Destination

class PregnancyAppState(
    val navController: NavHostController
) {

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun navigateToNavigationBar(route: String) {
        navController.navigate(route) {
            popUpTo(Destination.Home.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive }
        }
    }
}