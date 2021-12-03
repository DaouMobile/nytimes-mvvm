package com.daou.timesapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clip_article")
data class ClipArticle(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var linkUrl: String,
    var date: String,
    var title: String,
    var thumbnailUrl: String
)