package com.example.actearn.module

import com.example.actearn.repository.SharedRepository
import com.example.actearn.repository.SharedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun providesSharedRepository(sharedRepository: SharedRepositoryImpl): SharedRepository
}