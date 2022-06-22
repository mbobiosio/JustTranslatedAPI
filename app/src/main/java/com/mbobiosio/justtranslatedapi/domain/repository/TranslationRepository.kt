package com.mbobiosio.justtranslatedapi.domain.repository

import com.mbobiosio.justtranslatedapi.data.remote.model.TranslationDTO

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
interface TranslationRepository {

    suspend fun translateString(
        language: String,
        text: String
    ): TranslationDTO
}
