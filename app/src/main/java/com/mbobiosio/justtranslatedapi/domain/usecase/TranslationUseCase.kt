package com.mbobiosio.justtranslatedapi.domain.usecase

import com.mbobiosio.justtranslatedapi.data.mappers.mapRemoteDataToDomain
import com.mbobiosio.justtranslatedapi.domain.Resource
import com.mbobiosio.justtranslatedapi.domain.model.Translation
import com.mbobiosio.justtranslatedapi.domain.repository.TranslationRepository
import com.mbobiosio.justtranslatedapi.util.network.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
class TranslationUseCase @Inject constructor(
    private val repository: TranslationRepository
) {

    operator fun invoke(language: String, text: String): Flow<Resource<Translation>> = flow {
        emit(Resource.Loading)
        emit(
            safeApiCall {
                repository.translateString(language, text).mapRemoteDataToDomain()
            }
        )
    }.flowOn(Dispatchers.IO)
}
