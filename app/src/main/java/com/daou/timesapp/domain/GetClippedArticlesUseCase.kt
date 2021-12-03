package com.daou.timesapp.domain

import com.daou.timesapp.data.NewsRepository
import com.daou.timesapp.ui.clip.model.ClipArticleViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetClippedArticlesUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(): List<ClipArticleViewData> =
        withContext(Dispatchers.IO) {
            val result = repository.getAllClipArticles()
            result.map {
                ClipArticleViewData(
                    id = it.id,
                    title = it.title,
                    date = it.date,
                    linkUrl = it.linkUrl,
                    thumbnailUrl = it.thumbnailUrl
                )
            }
        }
}