package com.example.kakaoapplication.data

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .header("Authorization", "KakaoAK f3dcc343ab7d563dd151d474b3194c30")
                .build()
        )
    }
}