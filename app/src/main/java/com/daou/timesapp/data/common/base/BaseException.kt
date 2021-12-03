package com.daou.timesapp.data.common.base

data class BaseException(
    val code: String? = "",
    val errorMessage: String? = ""
) : Exception()