package com.mbobiosio.justtranslatedapi.domain.model

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
data class Translation(
    val code: Int,
    val lang: String,
    val text: List<String>
)
