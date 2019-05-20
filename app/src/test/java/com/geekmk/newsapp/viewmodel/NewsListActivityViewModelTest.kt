package com.geekmk.newsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geekmk.newsapp.BuildConfig
import com.geekmk.newsapp.data.NewsArticleRepository
import com.geekmk.newsapp.data.source.local.NewsArticleDao
import com.geekmk.newsapp.data.source.remote.NewsService
import com.geekmk.newsapp.ui.newslist.NewsArticlesViewModel
import com.geekmk.newsapp.utils.MockTestUtil
import com.geekmk.newsapp.utils.MockedUtil
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsListActivityViewModelTest {

    private lateinit var viewModel: NewsArticlesViewModel

    private lateinit var repository: NewsArticleRepository


    private val newsDao = mock<NewsArticleDao>()

    private val service = mock<NewsService>()


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repository = NewsArticleRepository(service, newsDao, MockedUtil())
        viewModel = NewsArticlesViewModel(repository, MockedUtil())
    }

    @Test
    fun loadNewsList() {
        val loadFromDB = MockTestUtil.mockArticleList()
        whenever(newsDao.queryNewsArticles()).thenReturn(Single.just(loadFromDB))

        whenever(service.getTopArticles("us", BuildConfig.API_KEY))
            .thenReturn(Observable.just(MockTestUtil.mockAPIResult()))


        val data = viewModel.disposableObserver

    }

}
