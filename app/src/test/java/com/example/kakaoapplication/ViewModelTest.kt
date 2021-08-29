package com.example.kakaoapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kakaoapplication.data.model.Document
import com.example.kakaoapplication.data.model.ImageResponse
import com.example.kakaoapplication.data.model.Meta
import com.example.kakaoapplication.repository.DaumRepository
import com.example.kakaoapplication.viewmodel.KakaoViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    /*@Rule
    @JvmField
    val rule : RxSchedulerRule = RxSchedulerRule()*/

    private lateinit var viewModel: KakaoViewModel

    @MockK
    private lateinit var repository: DaumRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = KakaoViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `repository test`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val documents = arrayListOf(
            Document(
                "2018-12-21T20:46:05.000+09:00",
                "티스토리",
                573,
                "http://t1.daumcdn.net/cfile/tistory/99AA874D5C1CCD7D12",
                1080
            )
        )
        val meta = Meta(false, 3998, 2857239)
        val list = ImageResponse(documents, meta)

        coEvery { repository.loadImages("제니", 1, 1) } returns list
        repository.loadImages("제니", 1, 1)
        coVerify { repository.loadImages("제니", 1, 1) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `view model test`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val documents = arrayListOf(
            Document(
                "2018-12-21T20:46:05.000+09:00",
                "티스토리",
                573,
                "http://t1.daumcdn.net/cfile/tistory/99AA874D5C1CCD7D12",
                1080
            )
        )
        val meta = Meta(false, 3998, 2857239)
        val list = ImageResponse(documents, meta)

        coEvery { repository.loadImages(any(), any(), any()) } returns list
        viewModel.fetchImages("제니", 1, 1)
        Assert.assertEquals(viewModel.imageList.value, list)
    }

    /*@Test
    fun testViewModel() {
        val documents = arrayListOf(
            Document(
                "2018-12-21T20:46:05.000+09:00",
                "티스토리",
                573,
                "http://t1.daumcdn.net/cfile/tistory/99AA874D5C1CCD7D12",
                1080
            )
        )
        val meta = Meta(false, 3998, 2857239)
        val list = ImageResponse(documents, meta)

        every { repository.loadImages(any(), any(), any()) } returns Single.just(list)

        viewModel.fetchImages("제니", 1, 1)
        Assert.assertEquals(viewModel.imageList.value, list)
    }*/
}