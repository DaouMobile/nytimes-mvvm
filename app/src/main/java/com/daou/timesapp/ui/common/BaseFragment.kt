package com.daou.timesapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.daou.timesapp.common.ext.showToast

abstract class BaseFragment<B : ViewDataBinding, V : BaseViewModel>(@LayoutRes private val layoutId: Int) :
    Fragment() {
    abstract val viewModel: V
    private var _binding: B? = null
    val binding: B get() = _binding!!

    abstract fun setBindingVariables()
    abstract fun subscribeViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        setBindingVariables()
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeCommon()
        subscribeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeCommon() {
        val progressCircle = ProgressSpinner(
            requireContext()
        ).apply {
            setCancelable(false)
        }
        viewModel.showToast.observe(viewLifecycleOwner) {
            requireContext().showToast(it)
        }
        viewModel.showToastStr.observe(viewLifecycleOwner) {
            requireContext().showToast(it)
        }
        viewModel.isProgress.observe(viewLifecycleOwner) {
            progressCircle.run {
                if (it) show() else dismiss()
            }
        }
    }
}