package com.example.securitytemplate.core.auth

import android.content.Context
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthManager(
    private val context: Context
) {

    companion object {
        const val SHARE_STORAGE_KEY = "SECURITY_TEMPLATE_STORAGE"
        const val TOKEN_KEY = "USER_TOKEN"
    }

    private val _stateSharedFlow: MutableSharedFlow<AuthState> =
        MutableSharedFlow(extraBufferCapacity = 1)
    val stateSharedFlow = _stateSharedFlow.asSharedFlow()

    var currentValue = AuthState.UN_AUTH
        private set

    init {
        _stateSharedFlow.tryEmit(currentValue)
    }

    fun changeState(newState: AuthState) {
        currentValue = newState
        _stateSharedFlow.tryEmit(newState)
    }

    fun checkToken(): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(SHARE_STORAGE_KEY, Context.MODE_PRIVATE)
        val token = sharedPreferences.getString(TOKEN_KEY, "")

        if (token != null && token.isNotEmpty()) {
            return true
        }
        return false
    }

    fun checkAuth(): Boolean {
        return (checkToken() && currentValue == AuthState.AUTH)
    }

    fun setToken(token: String): Unit {
        val sharedPreferences =
            context.getSharedPreferences(SHARE_STORAGE_KEY, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    fun removeToken(): Unit {
        val sharedPreferences =
            context.getSharedPreferences(SHARE_STORAGE_KEY, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(TOKEN_KEY)
            apply()
        }
    }
}
