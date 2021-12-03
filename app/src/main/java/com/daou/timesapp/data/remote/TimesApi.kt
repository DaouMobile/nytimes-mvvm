package com.daou.timesapp.data.remote

import com.daou.timesapp.data.remote.model.SearchNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TimesApi {
    @GET("svc/search/v2/articlesearch.json")
    suspend fun searchNews(
        @Query("q") text: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "newest"
    ): SearchNewsResponse
}