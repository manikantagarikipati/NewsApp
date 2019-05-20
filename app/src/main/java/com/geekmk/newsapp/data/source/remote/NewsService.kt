package com.geekmk.newsapp.data.source.remote

import com.geekmk.newsapp.BuildConfig
import com.geekmk.newsapp.data.model.TopNewsArticlesAPIResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("top-headlines")
    fun getTopArticles(@Query("country") country: String,
                       @Query("apiKey") apiKey: String = BuildConfig.API_KEY)
            : Observable<TopNewsArticlesAPIResponse>

}