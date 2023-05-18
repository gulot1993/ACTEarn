package com.example.actearn.module

import android.content.Context
import android.content.SharedPreferences
import com.example.actearn.core.PreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun providesPreferenceHelper(sharedPreferences: SharedPreferences): PreferenceHelper = PreferenceHelper(sharedPreferences)

    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences("actEarn",Context.MODE_PRIVATE)
}