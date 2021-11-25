package com.example.securitytemplate.core.navigation


class NavigationManager(startDestination: NavigationGroup) :
    BaseNavigationManager(startDestination) {

    override fun currentRouteGroup(): NavigationCommand {
        when {
            HomeDirections.isRoute(currentRoute) -> {
                currentRouteGroup = HomeDirections
            }
            else -> currentRouteGroup
        }
        return currentRouteGroup.root
    }
}