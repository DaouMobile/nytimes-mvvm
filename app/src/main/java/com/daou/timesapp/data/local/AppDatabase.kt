package com.daou.timesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daou.timesapp.data.local.model.ClipArticle
import com.daou.timesapp.data.local.model.SearchWord

@Database(entities = [ClipArticle::class, SearchWord::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun clipArticleDao(): ClipArticleDao
    abstract fun searchWordDao(): SearchWordDao
}