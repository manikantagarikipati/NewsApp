package com.geekmk.newsapp.ui.newsdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.geekmk.newsapp.R
import com.geekmk.newsapp.base.BaseActivity

import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.content_news_detail.*

const val URL_TO_LOAD = "urlToLoad"

class NewsDetailActivity : BaseActivity(), WebViewContract {


    companion object {

        fun getIntent(urlToLoad: String, context: Context): Intent {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra(URL_TO_LOAD, urlToLoad)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        wvNewsDetail.apply {
            settings.javaScriptEnabled = true
            webChromeClient = ChromeClient(this@NewsDetailActivity)
            webViewClient = StaticWebClient(this@NewsDetailActivity)
            showProgress()
            loadUrl(intent?.getStringExtra(URL_TO_LOAD))
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()

        finish()

        return true

    }

    override fun setTitle(title: String?) {
        toolbar.title = title
    }

    override fun showProgress() {
        showProgressView()
    }

    override fun hideProgress() {
        hideProgressView()
    }

    override fun showError(code: Int) {
        showView(code)
    }


}
