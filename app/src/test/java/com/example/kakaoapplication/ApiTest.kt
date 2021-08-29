package com.example.kakaoapplication

import com.example.kakaoapplication.data.service.DaumService
import com.example.kakaoapplication.di.NetworkModule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ApiTest {

    private val okHttpClient by lazy { NetworkModule.provideOkHttpClient() }
    private val retrofit by lazy { NetworkModule.provideRetrofit(okHttpClient) }
    //private val daumService by lazy { ServiceModule.provideService(retrofit) }

    @MockK
    private lateinit var daumService: DaumService

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @ExperimentalCoroutinesApi
    @Test
    fun testLoadImages() = coroutinesTestRule.testDispatcher.runBlockingTest {
        daumService.loadImages("로제", 1, 30)
        coVerify { daumService.loadImages("로제", 1, 30) }
    }

    /*@Test
    fun testLoadImages() {
        daumService.loadImages("로제", 1, 30)
            .test() // Observable을 TestObserver로 변환.
            .assertSubscribed()  // 성공적으로 onSubscribe가 호출되었는지 검증.
            .assertValue { data -> data.documents.size == 30 } // value가 일치하는지 검증.
            .assertComplete() // 정상적으로 onComplete가 호출되었는지 검증.
            .assertNoErrors()    // 에러없이 끝났는지 검증.

    }

    @Test
    fun testNotFoundError() {
        daumService.loadImages(";", 1, 30)
            .test()
            .assertError { error ->
                // error를 HttpException으로 캐스팅 후 status code가 500인지 확인.
                error is HttpException && error.code() == 500
            }
    }*/
}