package com.geekmk.newsapp.data

import android.util.Log
import com.geekmk.newsapp.data.model.NewsArticle
import com.geekmk.newsapp.data.source.local.NewsArticleDao
import com.geekmk.newsapp.data.source.remote.ApiInterface
import com.geekmk.newsapp.utils.Utils

import io.reactivex.Observable
import javax.inject.Inject

class NewsArticleRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val newsArticleDao: NewsArticleDao, private val utils: Utils
) {

    fun getTopArticles(): Observable<List<NewsArticle>> {

        val hasConnection = utils.isConnectedToInternet()
        var observableFromApi: Observable<List<NewsArticle>>? = null
        if (hasConnection) {
            observableFromApi = getArticlesFromApi()
        }
        val observableFromDb = getArticlesFromDb()

        return if (hasConnection) Observable.concatArrayEager(observableFromApi, observableFromDb)
        else observableFromDb
    }

    private fun getArticlesFromApi(): Observable<List<NewsArticle>> {
        return apiInterface.getTopArticles("us")
            .doOnNext {
                Log.e("REPOSITORY API * ", it.articles.size.toString())
                newsArticleDao.deleteAllArticles()
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
                Log.d("REPOSITORY DB *** ", it.size.toString())
            }
    }
}