package com.example.securitytemplate.ui.modules.shared.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.securitytemplate.core.auth.AuthManager
import com.example.securitytemplate.core.auth.AuthState
import com.example.securitytemplate.core.navigation.NavigationManager
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun AppBar(
    title: String,
    canPop: Boolean,
    navigationManager: NavigationManager,
    authManager: AuthManager,
) {
    TopAppBar(
        title = { Text(title) },
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .statusBarsPadding(),
        actions = {
            IconButton(
                onClick = {
                    navigationManager.setShowUIFlow(false)
                    authManager.removeToken()
                    authManager.changeState(AuthState.UN_AUTH)
                },
                content = {
                    Icon(
                        Icons.Rounded.ExitToApp,
                        "Logout",
                        tint = MaterialTheme.colors.onBackground
                    )
                })
        },
    )
}