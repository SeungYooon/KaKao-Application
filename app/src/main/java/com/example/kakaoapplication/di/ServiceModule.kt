package com.example.kakaoapplication.di

import com.example.kakaoapplication.data.service.DaumService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): DaumService =
        retrofit.create(DaumService::class.java)
}