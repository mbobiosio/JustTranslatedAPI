package com.mbobiosio.justtranslatedapi.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@JsonClass(generateAdapter = true)
data class TranslationDTO(
    @Json(name = "code")
    val code: Int,
    @Json(name = "lang")
    val lang: String,
    @Json(name = "text")
    val text: List<String>
)
