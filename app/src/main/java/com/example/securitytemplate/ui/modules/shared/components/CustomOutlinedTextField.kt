package com.example.securitytemplate.ui.modules.shared.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onValueChange: (TextFieldValue) -> Unit = {},
    colors: TextFieldColors,
    label: String = "",
    showPassword: Boolean = false
) {
    val relocation = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    OutlinedTextField(
        modifier = modifier
            .bringIntoViewRequester(relocation)
            .onFocusEvent {
                if (it.isFocused) scope.launch { delay(300); relocation.bringIntoView() }
            }
            .fillMaxWidth(),
        label = { Text(label) },
        value = textFieldValue,
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        colors = colors,
        visualTransformation = if (keyboardType != KeyboardType.Password || showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
    )

}