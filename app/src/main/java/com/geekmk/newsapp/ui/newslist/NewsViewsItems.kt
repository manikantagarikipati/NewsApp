package com.geekmk.newsapp.ui.newslist

import android.support.v7.widget.RecyclerView
import android.view.View
import com.geekmk.newsapp.data.model.NewsArticle
import kotlinx.android.synthetic.main.item_news_article.view.*

class NewsItemViewHolder(item: View): RecyclerView.ViewHolder(item){

    fun bind(newsArticle: NewsArticle){
        itemView.tvArticleTitle.text = newsArticle.title
    }
}