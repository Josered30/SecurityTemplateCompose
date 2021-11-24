package com.example.securitytemplate.core.navigation

abstract class NavigationGroup(val root: NavigationCommand) {
    abstract var default: NavigationCommand

    fun isMainRoute(navigationCommand: NavigationCommand): Boolean {
        val destination = navigationCommand.destination
        if (destination == root.destination || destination == default.destination) {
            return true
        }
        return false
    }

    fun isMainRoute(destination: String): Boolean {
        if (destination == root.destination || destination == default.destination) {
            return true
        }
        return false
    }

    fun isRoute(destination: String): Boolean {
        val aux = destination.split("/")[0]
        return aux == root.destination
    }
}
