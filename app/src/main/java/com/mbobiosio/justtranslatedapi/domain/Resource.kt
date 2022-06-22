package com.mbobiosio.justtranslatedapi.domain

import com.mbobiosio.justtranslatedapi.domain.model.ErrorResponse

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val error: ErrorResponse? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
