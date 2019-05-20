package com.geekmk.newsapp.ui.newsdetail

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.text.TextUtils
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.geekmk.newsapp.base.BaseActivity
import com.geekmk.newsapp.base.DynamicViewHandler


class StaticWebClient(val webViewContract: WebViewContract?) : WebViewClient() {


    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        webViewContract?.showProgress()
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        webViewContract?.hideProgress()
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
        super.onReceivedError(view, request, error)
        var failingUrl = ""
        if (request.url != null) {
            failingUrl = request.url.toString()
        }
        webError(view, failingUrl, error.errorCode)
    }

    override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        webError(view, failingUrl, errorCode)
    }

    private fun webError(view: WebView, failingUrl: String, errorCode: Int) {
        //If the failingUrl and view.getUrl matches then only it means that error came because of loading url
        // otherwise it means that error came because of some other .
        // Ex: It may get triggered also on JavaScript errors after the page loads
        if (!TextUtils.isEmpty(failingUrl) && !TextUtils.isEmpty(view.url) && failingUrl
                        .equals(view.url, ignoreCase = true)) {
            view.stopLoading()
            view.loadUrl("about:blank")
            when (errorCode) {
                ERROR_HOST_LOOKUP -> webViewContract?.showError((DynamicViewHandler.INTERNET))
                ERROR_TIMEOUT -> webViewContract?.showError(DynamicViewHandler.SERVER)
                else -> webViewContract?.showError(DynamicViewHandler.SERVER)
            }
        }
    }
}