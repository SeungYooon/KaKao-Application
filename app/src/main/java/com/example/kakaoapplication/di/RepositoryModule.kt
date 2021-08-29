package com.example.kakaoapplication.di

import com.example.kakaoapplication.data.DaumRepositoryImpl
import com.example.kakaoapplication.repository.DaumRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindDaumRepository(daumRepositoryImpl: DaumRepositoryImpl): DaumRepository
}