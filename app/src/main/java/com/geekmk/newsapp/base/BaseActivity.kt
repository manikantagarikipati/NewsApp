package com.geekmk.newsapp.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

abstract class BaseActivity : AppCompatActivity(), DynamicViewHandler.ErrorViewUser {
    private val dynamicView: DynamicViewHandler by lazy {
        DynamicViewHandler(window.decorView)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        dynamicView.user = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dynamicView.user = this
    }
    //displays the generic progress
    fun showProgressView() {
        dynamicView.show(DynamicViewHandler.PROGRESS)
    }

    //hides the generic progress
    fun hideProgressView() {
        dynamicView.hide()
    }

    fun showView(type: Int) {
        dynamicView.show(type)
    }

    override fun onDynamicViewCreated(errorView: View, errorCode: Int) {

    }
}