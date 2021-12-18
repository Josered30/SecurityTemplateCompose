package com.example.securitytemplate.core.auth

class AuthUser(
    val userId: String,
    val token: String,
    val refreshToken: String,
    val authState: AuthState
) {
    constructor() : this("", "", "", AuthState.UN_AUTH)
}
