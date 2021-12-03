package com.daou.timesapp.data.remote.model

data class SearchNewsResponse(
    val response: BaseResponse?
)

data class BaseResponse(
    val docs: List<Article>?
)