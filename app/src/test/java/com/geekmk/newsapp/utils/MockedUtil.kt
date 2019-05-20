package com.geekmk.newsapp.utils

class MockedUtil :Utils(null){
    override fun isConnectedToInternet(): Boolean = true
}