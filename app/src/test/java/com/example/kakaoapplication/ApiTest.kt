package com.example.kakaoapplication

import com.example.kakaoapplication.di.NetworkModule
import com.example.kakaoapplication.di.ServiceModule
import org.junit.Test
import retrofit2.HttpException

class ApiTest {
    private val okHttpClient by lazy { NetworkModule.provideOkHttpClient() }

    private val retrofit by lazy { NetworkModule.provideRetrofit(okHttpClient) }

    private val daumService by lazy { ServiceModule.provideService(retrofit) }

    @Test
    fun testLoadData() {
        daumService.loadImages("설현", 1, 30)
            .test()
            .assertNoErrors()
            .assertSubscribed()
            .assertValue { data -> data.documents.size == 30 }
            .dispose()
    }

    @Test
    fun testLoadError() {
        daumService.loadImages(";", 1, 30)
            .test()
            .assertError { error ->
                error is HttpException && error.code() == 500
            }
    }
}