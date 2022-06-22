package com.mbobiosio.justtranslatedapi.presentation.translation

import com.mbobiosio.justtranslatedapi.domain.model.ErrorResponse
import com.mbobiosio.justtranslatedapi.domain.model.Translation

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
sealed class UIState {
    object Loading : UIState()
    data class Error(val message: ErrorResponse? = null) : UIState()
    data class Success(val result: Translation) : UIState()
}
