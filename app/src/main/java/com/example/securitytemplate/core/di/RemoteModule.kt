package com.example.securitytemplate.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    private const val API_BASE_URL = "/"

//    @Singleton
//    @Provides
//    fun providesOkHttpClient(): OkHttpClient =
//        OkHttpClient
//            .Builder()
//            .readTimeout(5, TimeUnit.SECONDS)
//            .build()
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(API_BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

}