package com.geekmk.newsapp.ui.newsdetail

interface WebViewContract{


    fun setTitle(title:String?)

    fun showProgress()

    fun hideProgress()

    fun showError(code:Int)

}