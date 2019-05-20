package com.geekmk.newsapp.ui.newsdetail

import android.webkit.WebChromeClient
import android.webkit.WebView

/**
 * Created by manikanta.garikipati on 06/12/17.
 */

class ChromeClient(private val webViewContract: WebViewContract?) : WebChromeClient() {

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        webViewContract?.setTitle(title)
    }
}
