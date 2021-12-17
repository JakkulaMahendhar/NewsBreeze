package com.modules.newsbreeze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.modules.newsbreeze.R
import com.modules.newsbreeze.adapter.NewsAdapter
import com.modules.newsbreeze.model.Article
import com.modules.newsbreeze.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.common_bar.*
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.android.synthetic.main.fragment_saved.view.*


class SavedFragment : Fragment(), NewsAdapter.OnItemClickListener {

    lateinit var fragmentView: View
    lateinit var newsAdapter: NewsAdapter
    lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.fragment_saved, container, false)
        newsViewModel = (activity as MainActivity).viewModel
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleBack.visibility = View.VISIBLE
        saved.visibility = View.VISIBLE
        setupRecyclerView()
        newsViewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.differ.submitList(articles)
        })

        articleBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter( this,"saved")
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}