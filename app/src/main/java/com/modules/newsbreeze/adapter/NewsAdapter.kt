package com.modules.newsbreeze.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.modules.newsbreeze.R
import com.modules.newsbreeze.model.Article
import kotlinx.android.synthetic.main.news_view.view.*
import kotlinx.android.synthetic.main.saved_item_view.view.*

class NewsAdapter(var onItemClickListener: OnItemClickListener, var isFrom: String) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    lateinit var view: View

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        if (isFrom.equals("breaking")) {
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.news_view,
                parent,
                false
            )
        }
        if (isFrom.equals("saved")) {
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.saved_item_view,
                parent,
                false
            )
        }
        return NewsViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            if (isFrom.equals("breaking")) {
                Glide.with(this).load(article.urlToImage).into(news_icon)
                news_title.text = article.title
                news_content.text = article.description
                news_date.text = article.publishedAt

                holder.itemView.btn_read.setOnClickListener {
                    onItemClickListener.setOnItemClick(article)
                }

                holder.itemView.btn_save.setOnClickListener {
                    onItemClickListener.saveArticleToDb(article)
                }

            } else {
                Glide.with(this).load(article.urlToImage).into(img)
                content.text = article.title
                date.text = article.publishedAt
                reporterName.text = article.author
            }
        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener {
        fun setOnItemClick(article: Article) {

        }

        fun saveArticleToDb(article: Article) {

        }
    }
}