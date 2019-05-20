package com.geekmk.newsapp.ui.newslist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.geekmk.newsapp.base.NetworkErrorCode
import com.geekmk.newsapp.data.NewsArticleRepository
import com.geekmk.newsapp.data.model.NewsArticle
import com.geekmk.newsapp.utils.Utils
import com.geekmk.newsapp.utils.getLocalDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class NewsArticlesViewModel @Inject constructor(
    private val newsArticleRepository: NewsArticleRepository,private val utils: Utils
) : ViewModel() {

    var newsArticlesResult: MutableLiveData<List<NewsArticle>> = MutableLiveData()
    var newsArticlesError: MutableLiveData<Int> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<NewsArticle>>
    var newsArticlesLoader: MutableLiveData<Boolean> = MutableLiveData()


    fun newsArticlesResult(): LiveData<List<NewsArticle>> {
        return newsArticlesResult
    }

    fun newsArticlesError(): LiveData<Int> {
        return newsArticlesError
    }

    fun newsArticlesLoader(): LiveData<Boolean> {
        return newsArticlesLoader
    }

    fun loadTopNewsArticles() {

        disposableObserver = object : DisposableObserver<List<NewsArticle>>() {
            override fun onComplete() {

            }

            override fun onNext(newsArticles: List<NewsArticle>) {
                if(newsArticles.isEmpty() && !utils.isConnectedToInternet()){
                    newsArticlesError.postValue(NetworkErrorCode.ERROR_NO_INTERNET)
                }else{
                    for(article in newsArticles){
                        article.publishedAt = getLocalDate(article.publishedAt)
                    }
                    newsArticlesResult.postValue(newsArticles)
                    newsArticlesLoader.postValue(false)
                }
            }

            override fun onError(e: Throwable) {
                newsArticlesLoader.postValue(false)
                newsArticlesError.postValue(NetworkErrorCode.ERROR_UNKNOWN)
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
