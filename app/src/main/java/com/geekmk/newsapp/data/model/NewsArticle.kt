package com.geekmk.newsapp.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "newsarticle")
data class NewsArticle(

    @Json(name = "title")
    @ColumnInfo(name = "title")
    var title:String? = "",

    @Json(name = "description")
    @ColumnInfo(name = "description")
    var description:String? = "",

    @Json(name = "url")
    @ColumnInfo(name = "url")
    var url:String? = "",

    @Json(name = "urlToImage")
    @ColumnInfo(name = "url_to_image")
    var urlToImage:String? = "",

    @Json(name = "content")
    @ColumnInfo(name = "content")
    var content:String? = "",

    @Json(name = "publishedAt")
    @ColumnInfo(name = "published_at")
    var publishedAt:String?  = ""
){
    @PrimaryKey(autoGenerate = true)
    var articleId: Int = 0
}