package com.modules.newsbreeze.repository

import com.modules.newsbreeze.db.ArticleDatatbase
import com.modules.newsbreeze.model.Article
import com.modules.newsbreeze.utils.BaseRepository
import com.modules.newsbreeze.utils.NetworkResult
import com.modules.newsbreeze.utils.ServiceProvider

class NewsRepository(val db: ArticleDatatbase) : BaseRepository() {


    suspend fun getBreakingNews(countryCode:String,pageNumber:Int) : NetworkResult{
        return apiCalls {
                ServiceProvider.newsService.getBreakingNews(countryCode,pageNumber)
        }
    }

    suspend fun searchNews(query:String,pageNumber: Int) : NetworkResult{
        return apiCalls {
            ServiceProvider.newsService.searchForNews(query,pageNumber)
        }
    }

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()
}