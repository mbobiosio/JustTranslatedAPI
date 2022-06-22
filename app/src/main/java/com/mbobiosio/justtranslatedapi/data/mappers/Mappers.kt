package com.mbobiosio.justtranslatedapi.data.mappers

import com.mbobiosio.justtranslatedapi.data.remote.model.TranslationDTO
import com.mbobiosio.justtranslatedapi.domain.model.Translation

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
fun TranslationDTO.mapRemoteDataToDomain(): Translation =
    with(this) {
        Translation(
            code = code,
            lang = lang,
            text = text
        )
    }
