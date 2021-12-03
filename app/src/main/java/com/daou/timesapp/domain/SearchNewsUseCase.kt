package com.daou.timesapp.domain

import com.daou.timesapp.data.NewsRepository
import com.daou.timesapp.data.common.base.ApiResult
import com.daou.timesapp.ui.home.model.ArticleViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchNewsUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(searchText: String, page: Int = 0): List<ArticleViewData> {
        return when (val result = withContext(Dispatchers.IO) {
            repository.searchNews(searchText, page)
        }) {
            is ApiResult.Success -> {
                result.data.response?.docs?.map {
                    val url = it.multimedia?.firstOrNull()?.url
                    val thumbnailUrl = if (url.isNullOrBlank()) "" else BASE_URL + url
                    ArticleViewData(
                        title = it.headline?.main ?: "",
                        thumbnailUrl = thumbnailUrl,
                        linkUrl = it.webUrl ?: "",
                        date = it.pubDate ?: ""
                    )
                } ?: listOf()
            }
            is ApiResult.Failure -> {
                throw result.exception
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://www.nytimes.com/"
    }
}