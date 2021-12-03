package com.daou.timesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daou.timesapp.data.local.model.SearchWord

@Dao
interface SearchWordDao {
    @Query("SELECT * FROM search_word ORDER BY id DESC LIMIT 10")
    suspend fun getRecentSearchWords(): List<SearchWord>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchWord(word: SearchWord)

    @Query("DELETE FROM search_word")
    suspend fun deleteAllSearchWords()
}