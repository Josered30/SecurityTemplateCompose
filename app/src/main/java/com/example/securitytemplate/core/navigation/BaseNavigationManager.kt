package com.example.securitytemplate.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseNavigationManager(
    val startDestination: NavigationGroup,
    private val bottomNavigationGroups: List<NavigationGroup> = listOf(),
) {
    private val _appSharedFlow =
        MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)
    val appSharedFlow = _appSharedFlow.asSharedFlow()

    var currentIndexGroup = 0
    var canPop = false
    var currentRoute = ""

    init {
        val aux = bottomNavigationGroups.indexOf(startDestination)
        if (aux != -1) {
            currentIndexGroup = aux
        }
    }

    fun appNavigate(
        directions: NavigationCommand,
    ) {
        _appSharedFlow.tryEmit(directions)
    }

    fun currentRouteGroup(): Int {
        for (i in bottomNavigationGroups.indices) {
            if (bottomNavigationGroups[i].isRoute(currentRoute)) {
                currentIndexGroup = i
                break
            }
        }
        return currentIndexGroup
    }

    fun checkBottomNavRoute(destination: String): Boolean {
        for (group in bottomNavigationGroups) {
            if (group.isMainRoute(destination)) {
                return true
            }
        }
        return false
    }
}