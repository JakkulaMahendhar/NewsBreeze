package com.modules.newsbreeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.modules.newsbreeze.R
import com.modules.newsbreeze.db.ArticleDatatbase
import com.modules.newsbreeze.repository.NewsRepository
import com.modules.newsbreeze.viewmodel.NewsViewModel
import com.modules.newsbreeze.viewmodel.NewsViewmodelProvider

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository(ArticleDatatbase(this))
        val viewModelProviderFactory = NewsViewmodelProvider(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
    }
}