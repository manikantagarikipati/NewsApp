package com.geekmk.newsapp.ui.newslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.geekmk.newsapp.R
import com.geekmk.newsapp.data.model.NewsArticle
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class NewsListActivity : AppCompatActivity() {


    @Inject
    lateinit var newsArticlesViewModelFactory: NewsArticlesViewModelFactory
    lateinit var newsArticlesViewModel: NewsArticlesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        AndroidInjection.inject(this)

        newsArticlesViewModel = ViewModelProviders.of(this, newsArticlesViewModelFactory).get(
            NewsArticlesViewModel::class.java
        )

        newsArticlesViewModel.loadTopNewsArticles()

        newsArticlesViewModel.newsArticlesResult().observe(this,
            Observer<List<NewsArticle>> {
                tvResult.text = "Hello ${it?.size} articles"
            }
        )

        newsArticlesViewModel.newsArticlesError().observe(this, Observer<String> {
            tvResult.text = "Hello error $it"
        })

    }

    override fun onDestroy() {
        newsArticlesViewModel.disposeElements()
        super.onDestroy()
    }
}
