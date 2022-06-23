package com.mbobiosio.justtranslatedapi.util.network

import com.mbobiosio.justtranslatedapi.domain.NoInternetException
import com.mbobiosio.justtranslatedapi.domain.Resource
import com.mbobiosio.justtranslatedapi.domain.model.ErrorResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Resource<T> {
    return try {
        Resource.Success(apiCall())
    } catch (throwable: Throwable) {
        when (throwable) {
            is SocketTimeoutException -> Resource.Error(
                ErrorResponse("The connection request timed out. Please check your internet signal strength")
            )
            is NoInternetException -> Resource.Error(
                ErrorResponse("No internet connection")
            )
            is IOException -> Resource.Error(
                ErrorResponse("Connection detected without Internet access")
            )
            is HttpException -> {
                val message = throwableResponse(throwable)

                return Resource.Error(message)
            }
            else -> Resource.Error(ErrorResponse("An unexpected error occurred"))
        }
    }
}

private fun throwableResponse(e: HttpException): ErrorResponse? =
    try {
        e.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
                .adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (t: Throwable) {
        Timber.d("Error $t")
        null
    }
