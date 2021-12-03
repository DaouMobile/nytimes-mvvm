package com.daou.timesapp.data

import com.daou.timesapp.data.common.base.ApiResult
import com.daou.timesapp.data.common.base.BaseException
import com.daou.timesapp.data.local.ClipArticleDao
import com.daou.timesapp.data.local.SearchWordDao
import com.daou.timesapp.data.local.model.ClipArticle
import com.daou.timesapp.data.local.model.SearchWord
import com.daou.timesapp.data.remote.TimesApi
import com.daou.timesapp.data.remote.model.SearchNewsResponse
import com.daou.timesapp.ui.home.model.ArticleViewData
import retrofit2.HttpException

class NewsRepository(
    private val timesApi: TimesApi,
    private val searchWordDao: SearchWordDao,
    private val clipArticleDao: ClipArticleDao
) {
    suspend fun searchNews(
        searchText: String,
        page: Int = 0
    ): ApiResult<SearchNewsResponse> {
        return try {
            val res = timesApi.searchNews(text = searchText, page = page)
            ApiResult.Success(res)
        } catch (e: HttpException) {
            ApiResult.Failure(
                BaseException(
                    code = e.code().toString(),
                    errorMessage = e.response()?.message()
                )
            )
        } catch (e: Exception) {
            ApiResult.Failure(
                BaseException(
                    code = DEFAULT_ERROR_CODE,
                    errorMessage = e.message
                )
            )
        }
    }

    suspend fun getAllClipArticles(): List<ClipArticle> {
        return clipArticleDao.getAllClipArticle() ?: listOf()
    }

    suspend fun addClipArticle(article: ArticleViewData) {
        clipArticleDao.insertClipArticle(ClipArticle(
            linkUrl = article.linkUrl,
            date = article.date,
            title = article.title,
            thumbnailUrl = article.thumbnailUrl
        ))
    }

    suspend fun deleteClipArticle(id: Long) {
        clipArticleDao.deleteClipArticle(id)
    }

    suspend fun getRecentSearchWords(): List<SearchWord> {
        return searchWordDao.getRecentSearchWords() ?: listOf()
    }

    suspend fun addSearchWord(word: String) {
        searchWordDao.insertSearchWord(SearchWord(keyword = word))
    }

    companion object {
        private const val DEFAULT_ERROR_CODE = "9999"
    }
}