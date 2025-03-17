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
            return when {
                route == Splash.route -> Splash
                route == Onboarding.route -> Onboarding
                route == Login.route -> Login
                route == Register.route -> Register
                route.startsWith(Otp.route) -> Otp
                route == Home.route -> Home
                route.startsWith(Blogs.route) -> Blogs
                route.startsWith(Reminder.route) -> Reminder
                route.startsWith(Profile.route) -> Profile
                else -> Home
            }
        }

        private const val ROOT_ONBOARDING = "root_onboarding";

        private const val ROOT_AUTH = "root_auth";

        private const val ROOT_HOME = "root_home";

        private const val ROOT_PROFILE = "root_profile"

        private const val ROOT_REMINDER = "root_reminder"
    }

    data object Splash: Destination("splash", "Splash")

    data object RootOnboarding: Destination(ROOT_ONBOARDING, "Root Onboarding")

    data object Onboarding: Destination("${ROOT_ONBOARDING}/onboarding", "Onboarding")

    data object RootAuth: Destination(ROOT_AUTH, "Root Auth")

    data object Login: Destination("${ROOT_AUTH}/login", "Login")

    data object Register: Destination("${ROOT_AUTH}/register", "Register")

    data object Otp: Destination("${ROOT_AUTH}/otp", "Otp")

    data object RootHome: Destination(ROOT_HOME, "Root Home")

    data object Home: Destination("${ROOT_HOME}/home", "Home", Icons.Default.Home)

    data object Blogs: Destination("${ROOT_HOME}/blogs", "Blogs", Icons.AutoMirrored.Filled.List)

    data object Reminder: Destination(ROOT_REMINDER, "Reminder", Icons.Default.Notifications)

    data object AddReminder: Destination("${ROOT_REMINDER}/add", "Add Reminder", isRootDestination = false)

    data object Profile: Destination(ROOT_PROFILE, "Profile", Icons.Default.Person)

    data object HomeProfile: Destination("${ROOT_PROFILE}/home", "Home Profile")

    data object ProfileDetail: Destination("${ROOT_PROFILE}/detail", "Profile Detail", isRootDestination = false)

//    data object Settings: Destination("settings", "Settings", Icons.Default.Settings, isRootDestination = false)
//
//    data object Upgrade: Destination("upgrade", "Upgrade", Icons.Default.Star, isRootDestination = false)
//
//    data object Creation: Destination("creation", "Creation", isRootDestination = false)
//
//    data object Add: Destination("add", "Add", Icons.Default.Add, isRootDestination = false)
}