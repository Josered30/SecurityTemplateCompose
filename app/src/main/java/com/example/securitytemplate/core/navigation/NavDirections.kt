package com.example.securitytemplate.core.navigation

import androidx.navigation.NamedNavArgument


object AuthDirections : NavigationGroup(
    NavigationCommand(
        emptyList<NamedNavArgument>(),
        "authentication"
    )
) {
    val login = NavigationCommand(
        emptyList<NamedNavArgument>(),
        "${root.destination}/login"
    )
    val register = NavigationCommand(
        emptyList<NamedNavArgument>(),
        "${root.destination}/register"
    )
    override var default = login
}

object HomeDirections : NavigationGroup(
    NavigationCommand(
        emptyList<NamedNavArgument>(),
        "home"
    )
) {
    val home = NavigationCommand(
        emptyList<NamedNavArgument>(),
        "${root.destination}/home"
    )
    override var default = home
}

