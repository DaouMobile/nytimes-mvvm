package com.daou.timesapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_word")
data class SearchWord(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var keyword: String
)