package com.example.securitytemplate.ui.modules.shared.components;


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.securitytemplate.core.navigation.*

sealed class NavigationItem(
    val title: String,
    private val module: NavigationGroup,
    val icon: ImageVector
) {
    val navigationCommand: NavigationCommand = module.default

    object Home : NavigationItem(
        "Home",
        HomeDirections,
        Icons.Rounded.Home,
    )

    fun checkSelected(route: String): Boolean {
        return module.isMainRoute(route)
    }
}


@Composable
fun BottomBar(navigationManager: NavigationManager) {
    val items = listOf(
        NavigationItem.Home,
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
    ) {

        val currentRoute = navigationManager.currentRouteGroup().destination
        items.forEach { item ->

            val selected = item.checkSelected(currentRoute)

            BottomNavigationItem(
                icon = {
                    Icon(item.icon, item.title)
                },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                selected = selected,
                onClick = {
                    if (currentRoute != item.navigationCommand.destination) {
                        navigationManager.appNavigate(item.navigationCommand)
                    }
                },
            )
        }
    }
}
