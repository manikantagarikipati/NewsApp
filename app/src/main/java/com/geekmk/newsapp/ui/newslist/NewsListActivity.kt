package com.geekmk.newsapp.ui.newslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.geekmk.newsapp.R
import com.geekmk.newsapp.base.BaseActivity
import com.geekmk.newsapp.base.DynamicViewHandler
import com.geekmk.newsapp.base.NetworkErrorCode
import com.geekmk.newsapp.data.model.NewsArticle
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.content_news_list.*
import javax.inject.Inject

class NewsListActivity : BaseActivity() {


    @Inject
    lateinit var newsArticlesViewModelFactory: NewsArticlesViewModelFactory
    private lateinit var newsArticlesViewModel: NewsArticlesViewModel
    private var newsListAdapter = NewsListAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        setSupportActionBar(toolbar)
        AndroidInjection.inject(this)

        initializeRecycler()

        newsArticlesViewModel = ViewModelProviders.of(this, newsArticlesViewModelFactory).get(
            NewsArticlesViewModel::class.java
        )

        showProgressView()
        newsArticlesViewModel.loadTopNewsArticles()

        newsArticlesViewModel.newsArticlesResult().observe(this,
            Observer<List<NewsArticle>> {
                it?.let { newsList ->
                    newsListAdapter.addNewsItems(newsList)
                    rvNewsList.adapter = newsListAdapter
                }
            }
        )

        newsArticlesViewModel.newsArticlesError().observe(this, Observer<Int> {
            it?.let {
                showView(DynamicViewHandler.INTERNET)
            }
        })

        newsArticlesViewModel.newsArticlesLoader().observe(this, Observer<Boolean> {
            if (it == false) hideProgressView()
        })

    }

    private fun initializeRecycler() {
        rvNewsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)
        }
    }

    override fun onDestroy() {
        newsArticlesViewModel.disposeElements()
        super.onDestroy()
    }
}
