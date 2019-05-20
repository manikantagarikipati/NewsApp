package com.geekmk.newsapp.ui.newslist

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.geekmk.newsapp.R
import com.geekmk.newsapp.base.ViewClickCallBack
import com.geekmk.newsapp.base.loadURL
import com.geekmk.newsapp.data.model.NewsArticle
import kotlinx.android.synthetic.main.item_news_article.view.*

class NewsItemViewHolder(item: View,val viewClickCallBack: ViewClickCallBack?): androidx.recyclerview.widget.RecyclerView.ViewHolder(item){

    fun bind(newsArticle: NewsArticle){
        setTextIfAvailable(itemView.tvArticleDescription,newsArticle.description)
        setTextIfAvailable(itemView.tvArticleTitle,newsArticle.title)
        setTextIfAvailable(itemView.tvTime,newsArticle.publishedAt)
        itemView.ivNewsArticleImage.loadURL(newsArticle.urlToImage,true)
        itemView.setOnClickListener {
            if(!newsArticle.url.isNullOrEmpty()){
                viewClickCallBack?.onViewClicked(R.id.nav_news_detail,newsArticle.url!!)
            }
        }
    }

    private fun setTextIfAvailable(view:TextView, content:String?){
        if(content.isNullOrEmpty()){
            view.visibility = View.GONE
        }else{
            view.visibility = View.VISIBLE
            view.text = content
        }
    }
}