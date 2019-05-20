package com.geekmk.newsapp.data

import android.util.Log
import com.geekmk.newsapp.data.model.NewsArticle
import com.geekmk.newsapp.data.source.local.NewsArticleDao
import com.geekmk.newsapp.data.source.remote.ApiInterface
import com.geekmk.newsapp.utils.Utils

import io.reactivex.Observable
import javax.inject.Inject

class NewsArticleRepository @Inject constructor(val apiInterface: ApiInterface,
    val newsArticleDao: NewsArticleDao,val utils: Utils
) {

    fun getTopArticles(): Observable<List<NewsArticle>> {
        val observableFromApi = getArticlesFromApi()
        val observableFromDb = getArticlesFromDb()
        return Observable.concatArrayEager(observableFromApi, observableFromDb)
    }

    private fun getArticlesFromApi(): Observable<List<NewsArticle>> {
        return apiInterface.getTopArticles("us")
            .doOnNext {
                Log.e("REPOSITORY API * ", it.articles.size.toString())
                newsArticleDao.insertAllNewsArticles(it.articles)
            }.flatMap {
                Observable.just(it.articles)
            }
    }

    private fun getArticlesFromDb(): Observable<List<NewsArticle>> {
        return newsArticleDao.queryNewsArticles()
            .toObservable()
            .doOnNext {
                //Print log it.size :)
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }
    }
}