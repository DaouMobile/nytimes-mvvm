package com.daou.timesapp.ui.clip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daou.timesapp.R
import com.daou.timesapp.data.NewsRepository
import com.daou.timesapp.data.common.base.BaseException
import com.daou.timesapp.domain.GetClippedArticlesUseCase
import com.daou.timesapp.ui.clip.model.ClipArticleViewData
import com.daou.timesapp.ui.common.BaseViewModel
import com.daou.timesapp.ui.common.SingleLiveEvent
import java.lang.Exception

class ClipViewModel(
    private val repository: NewsRepository,
    private val getClippedArticlesUseCase: GetClippedArticlesUseCase
) : BaseViewModel() {
    private val _articleList: MutableLiveData<List<ClipArticleViewData>> = MutableLiveData()
    val articleList: LiveData<List<ClipArticleViewData>> = _articleList
    private val _clickSearchItem = SingleLiveEvent<String>()
    val clickSearchItem: LiveData<String> = _clickSearchItem

    fun onClickItem(item: ClipArticleViewData) {
        _clickSearchItem.value = item.linkUrl
    }

    fun onClickUnClipItem(item: ClipArticleViewData) {
        launch {
            showProgress()
            try {
                repository.deleteClipArticle(item.id)
                hideProgress()
                showToast(R.string.msg_remove_success)
                getClippedArticles()
            } catch (e: Exception) {
                Log.e("ClipViewModel", "Fail to remove item id: ${item.id}")
                hideProgress()
                showToast(R.string.msg_remove_fail)
            }
        }
    }

    fun getClippedArticles() {
        launch {
            showProgress()
            try {
                val result = getClippedArticlesUseCase()
                _articleList.value = result
            } catch (e: BaseException) {
                Log.e("getMoreArticles", "code: ${e.code}, message: ${e.errorMessage}")
                showToast(R.string.msg_error)
            }
            hideProgress()
        }
    }
}