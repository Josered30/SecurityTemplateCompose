package com.example.securitytemplate.ui.modules.shared.components;


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.securitytemplate.core.navigation.*

sealed class NavigationItem(
    val title: String,
    val module: NavigationGroup,
    val icon: ImageVector
) {
    object Home : NavigationItem(
        "Home",
        HomeDirections,
        Icons.Rounded.Home,
    )
}


@Composable
fun BottomBar(navigationManager: NavigationManager, currentRoute: String) {
    var selected by remember {
        mutableStateOf(-1)
    }
    val items = listOf(
        NavigationItem.Home
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
    ) {

        items.forEachIndexed { index, item ->
            selected = if (item.module.isRoute(currentRoute)) {
                index
            } else {
                -1
            }
            BottomNavigationItem(
                icon = {
                    Icon(
                        item.icon, item.title, tint = if (selected == index) {
                            Color.White
                        } else {
                            Color.Transparent
                        }
                    )
                },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                selected = selected == index,
                onClick = {
                    navigationManager.appNavigate(item.module.default.destination)
                },
            )
        }
    }
}
