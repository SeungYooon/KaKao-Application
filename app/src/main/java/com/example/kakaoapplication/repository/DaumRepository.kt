package com.example.kakaoapplication.repository

import com.example.kakaoapplication.data.model.ImageResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface DaumRepository {

    /*fun loadImages(
        query: String?,
        page: Int,
        size: Int
    ): Single<ImageResponse>*/

    suspend fun loadImages(
        query: String?,
        page: Int,
        size: Int
    ): ImageResponse
}
