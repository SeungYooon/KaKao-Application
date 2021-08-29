package com.example.kakaoapplication.data

import com.example.kakaoapplication.data.model.ImageResponse
import com.example.kakaoapplication.data.service.DaumService
import com.example.kakaoapplication.di.IoDispatcher
import com.example.kakaoapplication.repository.DaumRepository
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DaumRepositoryImpl @Inject constructor(
    private val daumService: DaumService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DaumRepository {

    override suspend fun loadImages(query: String?, page: Int, size: Int): ImageResponse =
        withContext(ioDispatcher) {
            daumService.loadImages(query, page, size)
        }


    /*override fun loadImages(query: String?, page: Int, size: Int): Single<ImageResponse> =
        daumService.loadImages(query, page, size)*/
}

