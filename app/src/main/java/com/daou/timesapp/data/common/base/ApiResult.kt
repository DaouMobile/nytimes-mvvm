package com.daou.timesapp.data.common.base

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T): ApiResult<T>()
    data class Failure(val exception: BaseException): ApiResult<Nothing>()
}
