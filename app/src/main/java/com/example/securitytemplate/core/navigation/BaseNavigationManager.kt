package com.example.securitytemplate.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseNavigationManager(
    val startDestination: NavigationGroup
) {
    private val _appSharedFlow =
        MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)
    val appSharedFlow = _appSharedFlow.asSharedFlow()

    protected var currentRouteGroup = startDestination

    var canPop = false
    var currentRoute = ""

    fun appNavigate(
        directions: NavigationCommand,
    ) {
        _appSharedFlow.tryEmit(directions)
    }

    open fun currentRouteGroup(): NavigationCommand {
        return currentRouteGroup.default
    }
}