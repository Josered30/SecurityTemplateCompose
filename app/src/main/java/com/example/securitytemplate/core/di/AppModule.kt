package com.example.securitytemplate.core.di

import android.content.Context
import com.example.securitytemplate.core.auth.AuthManager
import com.example.securitytemplate.core.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesNavigationManager() = NavigationManager()

    @Singleton
    @Provides
    fun providesAuthManager(@ApplicationContext context: Context): AuthManager {
        return AuthManager(context)
    }
}