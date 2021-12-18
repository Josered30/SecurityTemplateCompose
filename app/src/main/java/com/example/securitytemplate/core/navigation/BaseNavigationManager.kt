package com.example.securitytemplate.core.navigation

import androidx.navigation.NamedNavArgument
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


abstract class BaseNavigationManager(
    val startDestination: NavigationGroup,
) {

    private val _appSharedFlow =
        MutableSharedFlow<String>(extraBufferCapacity = 1)
    val appSharedFlow = _appSharedFlow.asSharedFlow()

    private val _showUIFlow = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val showUIFlow = _showUIFlow.asSharedFlow()

    private val _imeInsetsFlow = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val imeInsetsFlow = _imeInsetsFlow.asSharedFlow()


    var canPop = false
    var currentRoute = ""
    var lastRoute = ""


    fun setShowUIFlow(flag: Boolean) {
        _showUIFlow.tryEmit(flag)
    }

    fun setImeInsetsFlow(flag: Boolean) {
        _imeInsetsFlow.tryEmit(flag)
    }

    fun appNavigate(route: String) {
        _appSharedFlow.tryEmit(route)
    }
}