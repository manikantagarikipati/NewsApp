package com.geekmk.newsapp.base

/***
 * Custom defined error code for network error
 */
class NetworkErrorCode {
    companion object {
        const val ERROR_UNKNOWN: Int = 601
        const val ERROR_NO_INTERNET: Int = 602
        const val ERROR_TIME_OUT: Int = 603
    }
}
