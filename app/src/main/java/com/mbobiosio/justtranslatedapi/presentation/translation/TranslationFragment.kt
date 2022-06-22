package com.mbobiosio.justtranslatedapi.presentation.translation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mbobiosio.justtranslatedapi.R
import com.mbobiosio.justtranslatedapi.databinding.FragmentTranslationBinding
import com.mbobiosio.justtranslatedapi.domain.model.Translation
import com.mbobiosio.justtranslatedapi.util.getLanguageCode
import com.mbobiosio.justtranslatedapi.util.toast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@AndroidEntryPoint
class TranslationFragment : Fragment() {

    private lateinit var binding: FragmentTranslationBinding
    private var langCode: String = ""
    private val viewModel by viewModels<TranslationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTranslationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        viewModel.translation.observe(viewLifecycleOwner) { result ->
            when (result) {
                UIState(isLoading = true) -> {
                    updateProgress(result.isLoading)
                }
                UIState(result = result.result) -> {
                    updateUI(result.result)
                    updateProgress(result.isLoading)
                }
                UIState(error = result.error) -> {
                    showError(result.error)
                    updateProgress(result.isLoading)
                }
            }
        }
    }

    private fun setupViews() = with(binding) {
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
            val text = inputEditText.text.toString()
            when {
                text.isEmpty() -> {
                    requireActivity().toast(message = getString(R.string.empty_text))
                }
                langCode.isEmpty() -> {
                    requireActivity().toast(message = getString(R.string.select_language_error))
                }
                else -> {
                    langCode = getLanguageCode(language = langCode)
                    viewModel.handleTranslation(language = langCode, text)
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
