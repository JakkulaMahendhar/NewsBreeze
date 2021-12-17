package com.modules.newsbreeze.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.modules.newsbreeze.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

}