package com.mbobiosio.justtranslatedapi.domain

import com.mbobiosio.justtranslatedapi.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader(
                "X-RapidAPI-Key", BuildConfig.API_KEY
            )
        }.build()

        return chain.proceed(request)
    }
}
