package com.pregnancy.edu.common.base

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val route: String,
    val title: String,
    val icon: ImageVector? = null,
    val isRootDestination: Boolean = true
) {
    companion object {

        fun fromString(route: String): Destination {
            return when (route) {
                Splash.route -> Splash
                Onboarding.route -> Onboarding
                Login.route -> Login
                Register.route -> Register
                Home.route -> Home
                Blogs.route -> Blogs
                Reminder.route -> Reminder
                Profile.route -> Profile
                else -> Home
            }
        }

        const val ROOT_ONBOARDING = "root_onboarding";

        const val ROOT_AUTH = "root_auth";

        const val ROOT_HOME = "root_home";
    }

    data object Splash: Destination("splash", "Splash")

    data object RootOnboarding: Destination(ROOT_ONBOARDING, "Root Onboarding")

    data object Onboarding: Destination("${ROOT_ONBOARDING}/onboarding", "Onboarding")

    data object RootAuth: Destination(ROOT_AUTH, "Root Auth")

    data object Login: Destination("${ROOT_AUTH}/login", "Login")

    data object Register: Destination("${ROOT_AUTH}/register", "Register")

    data object RootHome: Destination(ROOT_HOME, "Root Home")

    data object Home: Destination("${ROOT_HOME}/home", "Home", Icons.Default.Home)

    data object Blogs: Destination("${ROOT_HOME}/blogs", "Blogs", Icons.AutoMirrored.Filled.List)

    data object Reminder: Destination("${ROOT_HOME}/reminder", "Reminder", Icons.Default.Notifications)

    data object Profile: Destination("${ROOT_HOME}/profile", "Profile", Icons.Default.Person)

//    data object Settings: Destination("settings", "Settings", Icons.Default.Settings, isRootDestination = false)
//
//    data object Upgrade: Destination("upgrade", "Upgrade", Icons.Default.Star, isRootDestination = false)
//
//    data object Creation: Destination("creation", "Creation", isRootDestination = false)
//
//    data object Add: Destination("add", "Add", Icons.Default.Add, isRootDestination = false)
}