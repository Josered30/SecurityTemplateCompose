package com.example.securitytemplate.core.navigation

import androidx.navigation.NamedNavArgument


class NavigationCommand(
    val arguments: List<NamedNavArgument>,
    val destination: String
)