package com.example.securitytemplate.core.navigation

object AuthDirections : NavigationGroup(
    NavigationRoute(
        "authentication",
        emptyList()
    )
) {
    val login = NavigationRoute(
        "${root.destination}/login",
        emptyList()
    )
    override var default = login
}

object HomeDirections : NavigationGroup(
    NavigationRoute(
        "home",
        emptyList()
    )
) {
    val home = NavigationRoute(
        "${root.destination}/home",
        emptyList()
    )
    override var default = home
}