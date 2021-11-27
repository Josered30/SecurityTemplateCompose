package com.example.securitytemplate.ui.modules.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.securitytemplate.ui.modules.shared.components.AppBar
import com.example.securitytemplate.ui.modules.shared.components.BottomBar
import com.example.securitytemplate.ui.viewModels.HomeViewModel
import com.google.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.delay


@Composable
fun HomePage(homeViewModel: HomeViewModel) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val navigationManager = homeViewModel.navigationManager
    val authManager = homeViewModel.authManager

    LaunchedEffect(Unit) {
        delay(10)
        navigationManager.setShowUIFlow(true)
        // navigationManager.setImeInsetsFlow(true)
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

