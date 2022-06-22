package com.mbobiosio.justtranslatedapi.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
abstract class BaseFragment<viewBinding : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    Fragment() {

    private var _binding: viewBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun setupViews()
}
