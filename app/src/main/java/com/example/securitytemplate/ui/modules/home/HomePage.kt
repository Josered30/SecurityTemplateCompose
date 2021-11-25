package com.example.securitytemplate.ui.modules.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.securitytemplate.ui.modules.shared.components.AppBar
import com.example.securitytemplate.ui.modules.shared.components.BottomBar
import com.example.securitytemplate.ui.viewModels.HomeViewModel
import com.google.accompanist.insets.navigationBarsPadding


@Composable
fun HomePage(homeViewModel: HomeViewModel) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            AppBar(
                "Inicio",
                homeViewModel.navigationManager.canPop,
                homeViewModel.navigationManager,
                homeViewModel.authManager
            )
        },
        scaffoldState = scaffoldState,
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            BottomBar(
                homeViewModel.navigationManager
            )
        }

    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding))
        Text(text = "Home")
    }
}

