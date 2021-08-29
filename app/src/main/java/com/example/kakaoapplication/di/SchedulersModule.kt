package com.example.kakaoapplication.di

import com.example.kakaoapplication.util.BaseSchedulerProvider
import com.example.kakaoapplication.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object SchedulersModule {
    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider
}