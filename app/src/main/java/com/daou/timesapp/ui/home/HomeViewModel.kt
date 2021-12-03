package com.daou.timesapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daou.timesapp.R
import com.daou.timesapp.data.NewsRepository
import com.daou.timesapp.data.common.base.BaseException
import com.daou.timesapp.domain.SearchNewsUseCase
import com.daou.timesapp.ui.common.BaseViewModel
import com.daou.timesapp.ui.common.SingleLiveEvent
import com.daou.timesapp.ui.home.model.ArticleViewData

class HomeViewModel(
    private val repository: NewsRepository,
    private val searchNewsUseCase: SearchNewsUseCase
) : BaseViewModel() {
    val keyword: MutableLiveData<String> = MutableLiveData("")
    val isSearchMode: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _searchList: MutableLiveData<List<ArticleViewData>> = MutableLiveData()
    val searchList: LiveData<List<ArticleViewData>> = _searchList
    private val _keywordList: MutableLiveData<List<String>> = MutableLiveData()
    val keywordList: LiveData<List<String>> = _keywordList
    private val _clickSearchItem = SingleLiveEvent<String>()
    val clickSearchItem: LiveData<String> = _clickSearchItem
    private val _clearEditFocus = SingleLiveEvent<Any>()
    val clearEditFocus: LiveData<Any> = _clearEditFocus
    private var currentPage = 0

    init {
        onClickSearch()
    }

    fun setSearchMode(isFocus: Boolean) {
        if (isFocus) {
            getSearchKeywords()
            isSearchMode.value = true
        } else isSearchMode.value = false
    }

    fun onClickHistoryKeyword(word: String) {
        keyword.value = word
    }

    fun onClickSearch() {
        _clearEditFocus.call()
        launch {
            showProgress()
            val word = keyword.value ?: ""
            currentPage = 0
            _searchList.value = listOf()
            searchNewKeyword(word)
            saveKeyWord(word)
            hideProgress()
        }
    }

    fun onClickItem(item: ArticleViewData) {
        _clickSearchItem.value = item.linkUrl
    }

    fun onClickClipItem(item: ArticleViewData) {
        launch {
            showProgress()
            try {
                repository.addClipArticle(item)
                hideProgress()
                showToast(R.string.msg_add_success)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Fail to add clip article. title: ${item.title}")
                hideProgress()
                showToast(R.string.msg_add_fail)
            }
        }
    }

    fun searchMore() {
        launch {
            try {
                val result = searchNewsUseCase(
                    searchText = keyword.value ?: "",
                    page = ++currentPage
                )
                if (result.isEmpty()) {
                    showToast(R.string.msg_not_more)
                } else {
                    val newList = searchList.value?.toMutableList() ?: mutableListOf()
                    newList.addAll(result)
                    _searchList.value = newList
                }
            } catch (e: BaseException) {
                Log.e("getImageList", "code: ${e.code}, message: ${e.errorMessage}")
                showToast(R.string.msg_error)
            }
        }
    }

    private fun getSearchKeywords() {
        launch {
            try {
                val result = repository.getRecentSearchWords()
                _keywordList.value = result.map { it.keyword }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Fail to get search keyword list. e: ${e.message}")
            }
        }
    }

    private suspend fun searchNewKeyword(keyword: String) {
        try {
            val result = searchNewsUseCase(
                searchText = keyword,
                page = 0
            )
            _searchList.value = result
        } catch (e: BaseException) {
            Log.e("getImageList", "code: ${e.code}, message: ${e.errorMessage}")
            showToast(R.string.msg_error)
            _searchList.value = listOf()
        }
    }

    private suspend fun saveKeyWord(keyword: String) {
        if (keyword.isBlank()) return
        try {
            repository.addSearchWord(keyword)
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Fail to add keyword: $keyword")
        }
    }
}