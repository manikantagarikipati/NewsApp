package com.geekmk.newsapp.ui.newslist

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.geekmk.newsapp.R
import com.geekmk.newsapp.base.inflate
import com.geekmk.newsapp.data.model.NewsArticle

class NewsListAdapter(var newsArticleRVS:MutableList<NewsArticle>): RecyclerView.Adapter<NewsItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(parent.inflate(R.layout.item_news_article))
    }

    override fun getItemCount(): Int  = newsArticleRVS.size

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(newsArticleRVS[position])
    }

    fun addNewsItems(updatedItems:List<NewsArticle>){
        val initPosition = newsArticleRVS.size
        newsArticleRVS.addAll(updatedItems)
        notifyItemRangeInserted(initPosition, newsArticleRVS.size)
    }
}


