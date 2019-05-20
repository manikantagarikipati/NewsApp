package com.geekmk.newsapp.data.model

data class TopNewsArticlesAPIResponse(
    val status:String?,
    val totalResults:Int?,
    val articles:List<NewsArticle>
)