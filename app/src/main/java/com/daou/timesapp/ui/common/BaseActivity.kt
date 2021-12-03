package com.daou.timesapp.ui.common

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<B : ViewDataBinding, V : BaseViewModel>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity() {
    abstract val viewModel: V
    lateinit var binding: B

    abstract fun setBindingVariables(binding: B)
    abstract fun onCreate(binding: B)
    protected abstract fun subscribeViewModel(viewModel: V)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        setBindingVariables(binding)
        with(binding) {
            lifecycleOwner = this@BaseActivity
            executePendingBindings()
        }
        onCreate(binding)
        subscribeViewModel(viewModel)
    }
}