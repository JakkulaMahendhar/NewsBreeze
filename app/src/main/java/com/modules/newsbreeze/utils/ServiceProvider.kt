package com.modules.newsbreeze.utils

import com.modules.newsbreeze.api.NewsService
import com.modules.newsbreeze.api.RetrofitInstance

object ServiceProvider {
    val newsService: NewsService by lazy { RetrofitInstance.getNewsClient(NewsService::class.java) }
}