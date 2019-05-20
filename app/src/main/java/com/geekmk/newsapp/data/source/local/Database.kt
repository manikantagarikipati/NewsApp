package com.geekmk.newsapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geekmk.newsapp.data.model.NewsArticle


@Database(entities = [NewsArticle::class], version = 1)
abstract class Database :RoomDatabase() {

    abstract fun newsArticlesDao(): NewsArticleDao
}