package com.geekmk.newsapp.api

import com.geekmk.newsapp.BuildConfig
import com.geekmk.newsapp.data.model.NewsArticle
import com.geekmk.newsapp.data.source.remote.NewsService
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import java.io.IOException


class MovieServiceTest : ApiAbstract<NewsService>() {
    private lateinit var service: NewsService
    var mSubscriber: TestSubscriber<List<NewsArticle>>? = null

    @Before
    fun initService() {
        this.service = createService(NewsService::class.java)
        mSubscriber = TestSubscriber()
    }


    @Throws(IOException::class)
    @Test
    fun fetchTopNewsCountSuccessTest() {
        enqueueResponse("/news_top_articles.json")

        service.getTopArticles("us",BuildConfig.API_KEY)
            .test()
            .assertNoErrors()
            .assertValue{
                it.totalResults!=null &&  it.totalResults!! > 0
            }
    }

    @Throws(IOException::class)
    @Test
    fun fetchTopNewsArticleSuccessTest() {
        enqueueResponse("/news_top_articles.json")

        service.getTopArticles("us",BuildConfig.API_KEY)
            .test()
            .assertNoErrors()
            .assertValue{
                !it.articles.isNullOrEmpty() && it.articles[0].title == "11 people killed in gun attack at bar in Brazil: reports - Fox News"
            }
    }
}