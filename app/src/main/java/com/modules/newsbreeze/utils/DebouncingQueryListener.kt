package com.modules.newsbreeze.utils

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.modules.newsbreeze.utils.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import kotlinx.coroutines.*

internal class DebouncingQueryListener(
    var lifecycle: Lifecycle,
    private var onDebouncingQueryTextChange: (String?) -> Unit,
    private var onDebouncingQueryTextSubmit: (String?) -> Unit
) : SearchView.OnQueryTextListener, LifecycleObserver {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var searchJob: Job? = null

    init {
        lifecycle.addObserver(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(SEARCH_NEWS_TIME_DELAY)
                onDebouncingQueryTextChange(newText)
            }
        }
        return false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        searchJob?.cancel()
    }
}