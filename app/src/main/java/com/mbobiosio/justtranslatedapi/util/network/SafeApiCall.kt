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
        Timber.d("Throwable $throwable")
        when (throwable) {
            is SocketTimeoutException -> Resource.Error(
                ErrorResponse("Your network timed out")
            )
            is NoInternetException -> Resource.Error(
                ErrorResponse("No internet")
            )
            is IOException -> Resource.Error(
                ErrorResponse("${throwable.message}")
            )
            is HttpException -> {
                // Timber.d("Code ${throwable.code()} : Message ${throwable.message()}")
                val message = throwableResponse(throwable)

                return when (throwable.code()) {
                    401 -> Resource.Error(ErrorResponse("Unauthorized"))
                    else -> Resource.Error(message)
                }

                // NetworkResult.Error(code, message)

                /*when (throwable.code()) {
                404 -> NetworkResult.OnNotAuthorized(
                    R.string.err_http_auth
                )
                else -> NetworkResult.OnUnexpected(
                    R.string.err_http_unknown
                )
            }*/
            }
            else -> Resource.Error(null)
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
