package com.pregnancy.edu.presentation.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pregnancy.edu.common.base.Destination
import com.pregnancy.edu.presentation.navigation.NavigationBarItem

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    onNavigate: (destination: Destination) -> Unit
) {

    NavigationBar(
        modifier = modifier
    ) {
        buildNavigationBarItems(
            currentDestination = currentDestination,
            onNavigate = onNavigate
        ).forEach {
            NavigationBarItem(
                selected = it.selected,
                icon = it.icon,
                onClick = it.onClick,
                label = it.label
            )
        }
    }
}


fun buildNavigationBarItems(
    currentDestination: Destination,
    onNavigate: (destination: Destination) -> Unit
): List<NavigationBarItem> {
    return listOf(
        Destination.Home,
        Destination.Blogs,
        Destination.Reminder,
        Destination.Profile
    ).map {
        NavigationBarItem(
            selected = currentDestination.route == it.route,
            icon = {
                it.icon?.let { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = it.route
                    )
                }
            },
            onClick = {
                onNavigate(it)
            },
            label = {
                Text(text = it.title)
            }
        )
    }
}