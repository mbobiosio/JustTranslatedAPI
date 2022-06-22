package com.mbobiosio.justtranslatedapi.presentation.translation

import com.mbobiosio.justtranslatedapi.domain.model.Translation

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
data class UIState(
    val isLoading: Boolean = false,
    val result: Translation? = null,
    val error: String = ""
)
