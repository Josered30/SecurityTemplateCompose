package com.example.securitytemplate.core.navigation


abstract class NavigationGroup(val root: NavigationRoute) {
    abstract var default: NavigationRoute

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