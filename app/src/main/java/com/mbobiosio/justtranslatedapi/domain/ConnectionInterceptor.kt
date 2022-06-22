package com.mbobiosio.justtranslatedapi.domain

import com.mbobiosio.justtranslatedapi.util.network.ConnectionObserver
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class ConnectionInterceptor @Inject constructor(
    private val connectionObserver: ConnectionObserver
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            if (connectionObserver.hasInternetConnection())
                return@runBlocking chain.proceed(chain.request())
            else
                throw NoInternetException()
        }
    }
}

class NoInternetException : IOException()
