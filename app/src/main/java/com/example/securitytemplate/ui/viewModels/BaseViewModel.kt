package com.example.securitytemplate.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.securitytemplate.core.auth.AuthManager
import com.example.securitytemplate.core.navigation.NavigationCommand
import com.example.securitytemplate.core.navigation.NavigationManager


abstract class BaseViewModel constructor(
    val navigationManager: NavigationManager,
    val authManager: AuthManager,
) : ViewModel() {

    open fun appNavigate(navigationCommand: NavigationCommand): Unit {
        navigationManager.appNavigate(navigationCommand)
    }
}