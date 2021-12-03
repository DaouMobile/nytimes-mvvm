package com.daou.timesapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("web_url")
    val webUrl: String?,
    @SerializedName("pub_date")
    val pubDate: String?,
    val headline: HeadLine?,
    val multimedia: List<MultiMedia>?
)

data class HeadLine(
    val main: String?
)

data class MultiMedia(
    val url: String?
)
