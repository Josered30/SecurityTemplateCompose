package com.example.securitytemplate.ui.viewModels

import com.example.securitytemplate.core.auth.AuthManager
import com.example.securitytemplate.core.navigation.NavigationManager
import com.example.securitytemplate.ui.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    authManager: AuthManager,
    navigationManager: NavigationManager,
) :
    BaseViewModel(navigationManager, authManager) {
}