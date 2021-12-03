package com.daou.timesapp.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel: ViewModel() {
    private val _showToast = SingleLiveEvent<Int>()
    val showToast: LiveData<Int> = _showToast
    private val _showToastStr = SingleLiveEvent<String>()
    val showToastStr: LiveData<String> = _showToastStr
    private val _isProgress = SingleLiveEvent<Boolean>()
    val isProgress: LiveData<Boolean> = _isProgress

    fun showToast(msg: Int) {
        _showToast.postValue(msg)
    }

    fun showToast(msg: String) {
        _showToastStr.postValue(msg)
    }

    fun showProgress() {
        _isProgress.value = true
    }

    fun hideProgress() {
        _isProgress.value = false
    }

    protected fun launch(
        context: CoroutineContext= EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context) {
        block()
    }
}