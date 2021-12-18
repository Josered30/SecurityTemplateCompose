package com.example.securitytemplate.ui.modules.home


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.securitytemplate.core.navigation.AuthDirections
import com.example.securitytemplate.ui.modules.shared.components.AppBar
import com.example.securitytemplate.ui.viewModels.HomeViewModel
import kotlinx.coroutines.delay


@Composable
fun HomePage(homeViewModel: HomeViewModel) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val navigationManager = homeViewModel.navigationManager
    val authManager = homeViewModel.authManager

    LaunchedEffect(Unit) {
        if(AuthDirections.isRoute(navigationManager.lastRoute)) {
            delay(500)
        } else {
            delay(10)
        }
        navigationManager.setShowUIFlow(true)
    }

    Scaffold(
        topBar = {
            AppBar(
                "Inicio",
                navigationManager.canPop,
                navigationManager,
                authManager
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Text(text = "Home")
    }
}

