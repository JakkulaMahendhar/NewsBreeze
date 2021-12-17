package com.modules.newsbreeze.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modules.newsbreeze.model.Article
import com.modules.newsbreeze.model.NewsResponse
import com.modules.newsbreeze.repository.NewsRepository
import com.modules.newsbreeze.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsViewModel(val repository: NewsRepository) : ViewModel() {


    val breakingNewsResponse :MutableLiveData<NetworkResult> = MutableLiveData()
    var breakingNewsPageNumber = 1
    var countryCode = "us"

    val searchNewsResponse :MutableLiveData<NetworkResult> = MutableLiveData()
    var searchNewsPageNumber = 1

    fun getBreakingNews(){
        viewModelScope.launch(Dispatchers.IO) {
            breakingNewsResponse.postValue(NetworkResult.Requesting)
            val response = repository.getBreakingNews(countryCode,breakingNewsPageNumber)
            withContext(Dispatchers.Main){
                breakingNewsResponse.postValue(response)
            }

        }

    }


    fun searchNews(query:String){
        viewModelScope.launch(Dispatchers.IO) {
            searchNewsResponse.postValue(NetworkResult.Requesting)
            val response = repository.searchNews(query,searchNewsPageNumber)
            withContext(Dispatchers.Main){
                searchNewsResponse.postValue(response)
            }
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun getSavedNews() = repository.getSavedNews()
}