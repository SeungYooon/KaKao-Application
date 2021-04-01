package com.example.kakaoapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kakaoapplication.data.model.Document
import com.example.kakaoapplication.data.model.ImageResponse
import com.example.kakaoapplication.data.model.Meta
import com.example.kakaoapplication.data.service.DaumService
import com.example.kakaoapplication.viewmodel.KakaoViewModel
import io.reactivex.Single
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class KakaoViewModelTest {
    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: DaumService

    private lateinit var viewModel: KakaoViewModel

    @Before
    fun setup() {
        viewModel = KakaoViewModel(service)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Verify ViewModel When Success`() {
        val mockData = Document(
            "news",
            "2021-02-26T10:54:35.000+09:00",
            "일간스포츠",
            702,
            "https://t1.daumcdn.net/news/202102/26/ilgansports/20210226105437796gadg.jpg",
            560
        )
        val mockList = ArrayList<Document>()
        mockList.add(mockData)
        val mockMeta = Meta(false, 1, 1)
        val list = ImageResponse(mockList, mockMeta)
        val testSingle = Single.just(list)

        Mockito.`when`(service.loadImages("설현", 1, 1)).thenReturn(testSingle)
        viewModel.fetchImages("설현", 1, 1)

        Assert.assertEquals(1, viewModel.imageLiveData.value?.documents?.size)
        Assert.assertEquals(false, viewModel.resultLiveData.value)
    }

    @Test
    fun `Verify ViewModel When Error`() {
        val testSingle = Single.error<ImageResponse>(Throwable())

        Mockito.`when`(service.loadImages(";", 1, 1)).thenReturn(testSingle)
        viewModel.fetchImages(";", 1, 1)

        Assert.assertEquals(0, viewModel.imageLiveData.value?.documents?.size)
        Assert.assertEquals(true, viewModel.resultLiveData.value)
    }
}