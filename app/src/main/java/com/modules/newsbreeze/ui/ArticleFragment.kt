package com.modules.newsbreeze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.modules.newsbreeze.R
import com.modules.newsbreeze.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : Fragment() {

    lateinit var fragmentView: View
    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.fragment_article,container,false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val article = args.article
        Glide.with(fragmentView.context).load(article.urlToImage).into(articleImg)
        articleDate.text = article.publishedAt
        articleTitle.text = article.title
        reporterName.text = article.author
        articleDesc.text = article.description
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        btn_save.setOnClickListener {
            viewModel.saveArticle(article)
        }
        articleBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}