package com.mbobiosio.justtranslatedapi.data.remote.repository

import com.mbobiosio.justtranslatedapi.data.remote.api.ApiService
import com.mbobiosio.justtranslatedapi.data.remote.model.TranslationDTO
import com.mbobiosio.justtranslatedapi.domain.repository.TranslationRepository
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class TranslationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TranslationRepository {

    override suspend fun translateString(language: String, text: String): TranslationDTO =
        apiService.translate(language, text)
}
