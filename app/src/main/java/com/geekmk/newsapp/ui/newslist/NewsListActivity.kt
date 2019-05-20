package com.geekmk.newsapp.ui.newslist

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.geekmk.newsapp.R
import com.geekmk.newsapp.base.BaseActivity
import com.geekmk.newsapp.base.DynamicViewHandler
import com.geekmk.newsapp.base.NetworkErrorCode
import com.geekmk.newsapp.base.ViewClickCallBack
import com.geekmk.newsapp.data.model.NewsArticle
import com.geekmk.newsapp.ui.newsdetail.NewsDetailActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.content_news_list.*
import kotlinx.android.synthetic.main.partial_error_no_internet.view.*
import javax.inject.Inject

class NewsListActivity : BaseActivity(), ViewClickCallBack {


    @Inject
    lateinit var newsArticlesViewModelFactory: NewsArticlesViewModelFactory
    private lateinit var newsArticlesViewModel: NewsArticlesViewModel
    private var newsListAdapter = NewsListAdapter(mutableListOf(), this)

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
            it?.let { errorCode ->
                val viewId = when (errorCode) {
                    NetworkErrorCode.ERROR_UNKNOWN, NetworkErrorCode.ERROR_TIME_OUT -> DynamicViewHandler.SERVER
                    else -> DynamicViewHandler.INTERNET
                }
                showView(viewId)
            }
        })

        newsArticlesViewModel.newsArticlesLoader().observe(this, Observer<Boolean> {
            if (it == false) hideProgressView()
        })

    }

    private fun initializeRecycler() {
        rvNewsList.apply {
            setHasFixedSize(true)
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this.context,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
        }
    }

    override fun onDestroy() {
        newsArticlesViewModel.disposeElements()
        super.onDestroy()
    }

    override fun onViewClicked(id: Int, data: Any) {
        when (id) {
            R.id.nav_news_detail -> {
                startActivity(NewsDetailActivity.getIntent(data as String, this))
            }
        }
    }


    override fun onDynamicViewCreated(errorView: View, errorCode: Int) {
        errorView.also {
            it.noInternetTryAgain?.setOnClickListener {
                showProgressView()
                newsArticlesViewModel.loadTopNewsArticles()
            }

        }

    }
}
