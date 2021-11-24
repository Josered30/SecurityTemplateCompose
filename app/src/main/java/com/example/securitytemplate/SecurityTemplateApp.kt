package com.example.securitytemplate

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SecurityTemplateApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}