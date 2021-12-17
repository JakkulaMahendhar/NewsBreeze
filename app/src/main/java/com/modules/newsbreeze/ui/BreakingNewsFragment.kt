package com.modules.newsbreeze.ui

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modules.newsbreeze.R
import com.modules.newsbreeze.adapter.NewsAdapter
import com.modules.newsbreeze.model.Article
import com.modules.newsbreeze.model.NewsResponse
import com.modules.newsbreeze.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.modules.newsbreeze.utils.DebouncingQueryListener
import com.modules.newsbreeze.utils.NetworkResult
import com.modules.newsbreeze.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.common_bar.*
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment(), NewsAdapter.OnItemClickListener {

    lateinit var newsViewModel: NewsViewModel
    val TAG = "BreakingNewsFragment"
    private var fragmentView: View? = null
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_breaking_news, container, false)

            newsViewModel = (activity as MainActivity).viewModel
            newsViewModel.breakingNewsResponse.observe(viewLifecycleOwner, breakingNewsObserver)
            newsViewModel.searchNewsResponse.observe(viewLifecycleOwner, serachResponseObserver)
            newsViewModel.getBreakingNews()
            newsAdapter = NewsAdapter( this,"breaking")
        }
        return fragmentView
    }

    val serachResponseObserver = Observer<NetworkResult> { response ->
        when (response) {
            is NetworkResult.Requesting -> {
                showProgressBar()
            }
            is NetworkResult.Success<*> -> {
                hideProgressBar()
                val newsResponse = response.response.body() as? NewsResponse
                if (newsResponse != null) {
                    if(newsResponse.articles.size > 0) {
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                    }else{
                        Log.d("TAG","empty data")
                    }
                }
            }
            is NetworkResult.Failure -> {
                hideProgressBar()
            }
        }
    }

    val breakingNewsObserver = Observer<NetworkResult> { response ->
        when (response) {
            is NetworkResult.Requesting -> {
                showProgressBar()
            }

            is NetworkResult.Success<*> -> {
                hideProgressBar()
                val newsResponse = response.response?.body() as? NewsResponse
                newsResponse?.let {
                    newsAdapter.differ.submitList(newsResponse.articles.toList())
                }

            }

            is NetworkResult.Failure -> {
                hideProgressBar()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val face: Typeface? =
            fragmentView?.context?.let { ResourcesCompat.getFont(it, R.font.montserrat_medium) }
        val searchText =
            searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as TextView
        searchText.typeface = face
        app_icon.visibility = View.VISIBLE
        savedArticles.visibility = View.VISIBLE
        registerSearchListener()
        setupRecyclerView()
        savedArticles.setOnClickListener {
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_savedFragment
            )
        }
    }

    fun registerSearchListener() {
        val debouncingQueryListener =
            DebouncingQueryListener(lifecycle, onDebouncingQueryTextChange = { newText ->
                newText?.let {
                    if (it.isEmpty()) {
                        newsViewModel.getBreakingNews()
                    } else {
                        newsViewModel.searchNews(it)
                    }
                }
            }, onDebouncingQueryTextSubmit = {
                searchView.clearFocus()
            })

        searchView.setOnQueryTextListener(debouncingQueryListener)
    }

    private fun hideProgressBar() {
        pBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        pBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {

        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun setOnItemClick(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        findNavController().navigate(
            R.id.action_breakingNewsFragment_to_articleFragment,
            bundle
        )
    }

    override fun saveArticleToDb(article: Article) {
        newsViewModel.saveArticle(article)
    }
}