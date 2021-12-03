package com.daou.timesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daou.timesapp.data.local.model.ClipArticle

@Dao
interface ClipArticleDao {
    @Query("SELECT * FROM clip_article")
    suspend fun getAllClipArticle(): List<ClipArticle>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClipArticle(article: ClipArticle)

    @Query("DELETE FROM clip_article WHERE id=:id")
    suspend fun deleteClipArticle(id: Long)
}