package com.geekmk.newsapp.ui.newslist

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.geekmk.newsapp.R
import com.geekmk.newsapp.base.ViewClickCallBack
import com.geekmk.newsapp.base.inflate
import com.geekmk.newsapp.data.model.NewsArticle

class NewsListAdapter(var newsArticleRVS:MutableList<NewsArticle>, private val viewClickCallBack: ViewClickCallBack?): androidx.recyclerview.widget.RecyclerView.Adapter<NewsItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(parent.inflate(R.layout.item_news_article),viewClickCallBack)
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


