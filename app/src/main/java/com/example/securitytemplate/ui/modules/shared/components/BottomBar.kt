package com.example.securitytemplate.ui.modules.shared.components;


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
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
fun BottomBar(appNavigate: (NavigationCommand) -> Unit = {}, currentRoute: String?) {

    var selected by remember {
        mutableStateOf(0)
    }

    val items = listOf(
        NavigationItem.Home,
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {

        items.forEachIndexed { index, item ->
            selected = if (item.checkSelected(currentRoute.orEmpty())) {
                index
            } else {
                selected
            }
            BottomNavigationItem(
                icon = {
                    Icon(
                        item.icon, item.title,
                        tint = if (selected == index) {
                            Color.Red
                        } else {
                            Color.Blue
                        }
                    )
                },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                selected = selected == index,
                onClick = {
                    if (currentRoute != item.navigationCommand.destination) {
                        appNavigate(item.navigationCommand)
                    }
                },
            )
        }
    }
}
