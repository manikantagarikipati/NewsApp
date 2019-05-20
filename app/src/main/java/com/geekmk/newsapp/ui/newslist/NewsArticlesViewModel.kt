package com.geekmk.newsapp.ui.newslist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.geekmk.newsapp.data.NewsArticleRepository
import com.geekmk.newsapp.data.model.NewsArticle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class NewsArticlesViewModel @Inject constructor(
    private val newsArticleRepository: NewsArticleRepository) : ViewModel() {

    var newsArticlesResult: MutableLiveData<List<NewsArticle>> = MutableLiveData()
    var newsArticlesError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<NewsArticle>>

    fun newsArticlesResult(): LiveData<List<NewsArticle>> {
        return newsArticlesResult
    }

    fun newsArticlesError(): LiveData<String> {
        return newsArticlesError
    }

    fun loadTopNewsArticles() {

        disposableObserver = object : DisposableObserver<List<NewsArticle>>() {
            override fun onComplete() {

            }

            override fun onNext(newsArticles: List<NewsArticle>) {
                newsArticlesResult.postValue(newsArticles)
            }

            override fun onError(e: Throwable) {
                newsArticlesError.postValue(e.message)
            }
        }

        newsArticleRepository.getTopArticles()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements(){
        if(null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }

}
