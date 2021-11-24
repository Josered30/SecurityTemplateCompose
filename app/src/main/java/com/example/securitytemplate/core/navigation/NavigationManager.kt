package com.example.securitytemplate.core.navigation


import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigationManager {
    private val _appSharedFlow =
        MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)
    val appSharedFlow = _appSharedFlow.asSharedFlow()

    var canPop = false
    var currentRoute = ""

    fun appNavigate(
        directions: NavigationCommand,
    ) {
        _appSharedFlow.tryEmit(directions)
    }
}
