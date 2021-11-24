package com.example.securitytemplate.ui.viewModels


import com.example.securitytemplate.core.auth.AuthManager
import com.example.securitytemplate.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    navigationManager: NavigationManager, authManager: AuthManager
) : BaseViewModel(navigationManager, authManager) {

}