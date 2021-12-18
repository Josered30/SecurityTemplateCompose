package com.example.securitytemplate.core.auth

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthManager(
    private val context: Context
) {
    companion object {
        const val SHARE_STORAGE_KEY = "CHAT_APP_STORAGE"
        const val DATA_KEY = "USER_DATA"
    }

    private val _stateSharedFlow: MutableSharedFlow<AuthUser> =
        MutableSharedFlow(extraBufferCapacity = 1)
    val stateSharedFlow = _stateSharedFlow.asSharedFlow()

    var currentValue: AuthUser

    init {
        currentValue = if (checkData()) {
            getData()
        } else {
            AuthUser()
        }
        _stateSharedFlow.tryEmit(currentValue)
    }

    fun authenticate(userId: String, token: String, refreshToken: String) {
        val authUser = AuthUser(userId, token, refreshToken, AuthState.AUTH)
        setData(authUser)
        changeState(authUser)
    }

    fun unAuthenticate() {
        removeData()
        changeState(AuthUser())
    }

    fun isAuthenticated(): Boolean {
        if (currentValue.authState == AuthState.AUTH) {
            return true
        }
        return false
    }

    private fun changeState(authUser: AuthUser) {
        currentValue = authUser
        _stateSharedFlow.tryEmit(authUser)
    }

    private fun checkData(): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(SHARE_STORAGE_KEY, Context.MODE_PRIVATE)
        val authUser = sharedPreferences.getString(DATA_KEY, "")
        if (authUser != null && authUser.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun setData(authUser: AuthUser) {
        val sharedPreferences =
            context.getSharedPreferences(SHARE_STORAGE_KEY, Context.MODE_PRIVATE)
        val gson = Gson()
        with(sharedPreferences.edit()) {
            putString(DATA_KEY, gson.toJson(authUser))
            apply()
        }
    }

    private fun getData(): AuthUser {
        val sharedPreferences =
            context.getSharedPreferences(SHARE_STORAGE_KEY, Context.MODE_PRIVATE)
        val gson = Gson()
        val authUserString = sharedPreferences.getString(DATA_KEY, "") ?: return AuthUser()
        return gson.fromJson(authUserString, AuthUser::class.java)
    }

    private fun removeData(): Unit {
        val sharedPreferences =
            context.getSharedPreferences(SHARE_STORAGE_KEY, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(DATA_KEY)
            apply()
        }
    }
}
