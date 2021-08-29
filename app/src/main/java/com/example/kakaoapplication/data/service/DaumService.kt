package com.example.kakaoapplication.data.service

import com.example.kakaoapplication.data.model.ImageResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface DaumService {

    /*@GET("v2/search/image")
    fun loadImages(
        @Query("query") query: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<ImageResponse>*/

    @GET("v2/search/image")
    suspend fun loadImages(
        @Query("query") query: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ImageResponse
}