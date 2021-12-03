package com.daou.timesapp.ui.home.model

import com.daou.timesapp.common.ext.convertMilliSec
import java.text.SimpleDateFormat
import java.util.*

data class ArticleViewData(
    val title: String,
    val date: String,
    val linkUrl: String,
    val thumbnailUrl: String,
) {
    fun formatDateTime(): String {
        val timeInMillis = date.convertMilliSec()
        if (timeInMillis == 0L) return ""
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        return formatter.format(calendar.time)
    }
}