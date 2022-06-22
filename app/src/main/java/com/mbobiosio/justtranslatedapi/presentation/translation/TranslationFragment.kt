package com.mbobiosio.justtranslatedapi.presentation.translation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mbobiosio.justtranslatedapi.databinding.FragmentTranslationBinding
import com.mbobiosio.justtranslatedapi.domain.model.Translation
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@AndroidEntryPoint
class TranslationFragment : Fragment() {

    private lateinit var binding: FragmentTranslationBinding

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

        val input =
            "This article explores different ways to convert a list to a string in Kotlin. All elements in the specified list should be joined into a single string separated by a delimiter. The solution should not add the delimiter before or after the string."

        viewModel.handleTranslation("fr", text = input)

        viewModel.translation.observe(viewLifecycleOwner) { result ->
            when (result) {
                UIState(isLoading = true) -> {
                    updateProgress(result.isLoading)
                }
                UIState(result = result.result) -> {
                    updateUI(result.result)
                }
                UIState(error = result.error) -> {
                    showError(result.error)
                }
            }
        }
    }

    private fun updateProgress(state: Boolean) = with(binding) {
        Timber.d("State $state")
    }

    private fun updateUI(translation: Translation?) = with(binding) {
        translation?.let {
            it.text.forEach { text ->
                Timber.d("Text $text")
            }
            Timber.d("Result ${it.text}")
        }
    }

    private fun showError(error: String) {
        Timber.d("Error $error")
    }
}
