package com.mbobiosio.justtranslatedapi.presentation.translation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbobiosio.justtranslatedapi.domain.Resource
import com.mbobiosio.justtranslatedapi.domain.usecase.TranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val getTranslationUseCase: TranslationUseCase
) : ViewModel() {

    private val _uIState: MutableLiveData<UIState> = MutableLiveData()
    val translation: LiveData<UIState> get() = _uIState

    fun handleTranslation(language: String, text: String) {
        getTranslationUseCase.invoke(language, text).onEach { result ->
            _uIState.value = when (result) {
                is Resource.Loading -> UIState.Loading
                is Resource.Error -> UIState.Error(result.error)
                is Resource.Success -> UIState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }
}
