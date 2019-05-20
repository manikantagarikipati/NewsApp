package com.geekmk.newsapp.base

import android.support.annotation.LayoutRes
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(this.context).inflate(layoutRes, this, false)

fun AppCompatImageView.loadURL(url: String, isCrop :Boolean=false) {
    if(isCrop){
        Picasso.get().load(url).fit().into(this)
    }else{
        Picasso.get().load(url).into(this)
    }
}