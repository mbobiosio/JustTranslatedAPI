package com.mbobiosio.justtranslatedapi.presentation.translation

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mbobiosio.justtranslatedapi.R
import com.mbobiosio.justtranslatedapi.databinding.FragmentTranslationBinding
import com.mbobiosio.justtranslatedapi.domain.model.Translation
import com.mbobiosio.justtranslatedapi.presentation.base.BaseFragment
import com.mbobiosio.justtranslatedapi.util.getLanguageCode
import com.mbobiosio.justtranslatedapi.util.hideKeyboard
import com.mbobiosio.justtranslatedapi.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@AndroidEntryPoint
class TranslationFragment :
    BaseFragment<FragmentTranslationBinding>(R.layout.fragment_translation) {

    private var langCode: String = ""
    private val translationViewModel by viewModels<TranslationViewModel>()

    override fun setupViews() {
        initViews()

        // observe UI State
        observeViewModel()
    }

    private fun initViews() = with(binding) {

        // Setup Spinner
        spinner.apply {
            lifecycleOwner = this@TranslationFragment

            setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
                langCode = newItem
            }

            setOnSpinnerOutsideTouchListener { _, motionEvent ->
                if (motionEvent.actionButton == 0) {
                    spinner.dismiss()
                }
            }
        }

        // Set click listener on Floating Action Button
        translate.setOnClickListener {
            // hide keyboard
            it.hideKeyboard()

            // get string from edittext
            val text = inputEditText.text.toString()
            when {
                text.isEmpty() -> {
                    requireActivity().toast(message = getString(R.string.empty_text))
                }
                langCode.isEmpty() -> {
                    requireActivity().toast(message = getString(R.string.select_language_error))
                }
                else -> {
                    // extract language code from string
                    langCode = getLanguageCode(language = langCode)

                    // make translation request
                    translationViewModel.handleTranslation(language = langCode, text)
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            translationViewModel.translation.observe(viewLifecycleOwner) { result ->
                when (result) {
                    UIState.Loading -> {
                        updateProgress(true)
                    }
                    is UIState.Success -> {
                        updateUI(result.result)
                        updateProgress(false)
                    }
                    is UIState.Error -> {
                        showError(result.message?.message)
                        updateProgress(false)
                    }
                }
            }
        }
    }

    private fun updateProgress(state: Boolean) = with(binding) {
        progress.isVisible = state
    }

    private fun updateUI(translation: Translation?) = with(binding) {
        translation?.let {
            it.text.forEach { text ->
                translationTextView.text = text
            }
        }
    }

    private fun showError(error: String?) = with(binding) {
        error?.let {
            errorMessage.isVisible = error.isNotEmpty()
            errorMessage.text = error
            Timber.d("Error $error")
        }
    }
}
