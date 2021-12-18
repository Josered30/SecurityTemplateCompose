package com.example.securitytemplate.ui.modules.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.securitytemplate.core.navigation.BaseNavigationManager
import com.example.securitytemplate.ui.viewModels.LoginViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay

@Composable
fun LoginPage(loginViewModel: LoginViewModel) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val navigationManager = loginViewModel.navigationManager

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.navigationBarsPadding(),
    ) {
        LoginPageComponent(loginViewModel.navigationManager, loginViewModel::login)
    }

}

@Composable
fun LoginPageComponent(
    navigationManager: BaseNavigationManager,
    login: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val tfCorreo = remember { mutableStateOf(TextFieldValue()) }
        val tfContraseña = remember { mutableStateOf(TextFieldValue()) }

        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = tfCorreo.value,
                onValueChange = { tfCorreo.value = it },
                label = { Text("Correo") },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            OutlinedTextField(
                value = tfContraseña.value,
                onValueChange = { tfContraseña.value = it },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            Button(
                onClick = {
                    login()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 100.dp, end = 100.dp, top = 20.dp, bottom = 0.dp)
                    .clip(RoundedCornerShape(10)),

                ) {
                Text("Ingresar")
            }
        }
    }
}
