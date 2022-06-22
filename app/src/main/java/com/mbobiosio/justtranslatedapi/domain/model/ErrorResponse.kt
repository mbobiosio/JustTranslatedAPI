package com.mbobiosio.justtranslatedapi.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class ErrorResponse(

    @Json(name = "message")
    val message: String
)
