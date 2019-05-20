package com.geekmk.newsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


open class Utils @Inject constructor(private val context: Context?) {

    open fun isConnectedToInternet(): Boolean {
        val connectivity = context?.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        if (info != null)
            for (i in info.indices)
                if (info[i].state == NetworkInfo.State.CONNECTED) {
                    return true
                }
        return false
    }
}

fun getLocalDate(serverDate: String?): String {
    return try {

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val value = formatter.parse(serverDate)

        val dateFormatter = SimpleDateFormat("MMM-dd HH:mm") //this format changeable
        dateFormatter.timeZone = TimeZone.getDefault()
        dateFormatter.format(value)

        //Log.d("ourDate", ourDate);
    } catch (e: Exception) {
        "00-00 00:00"
    }
}