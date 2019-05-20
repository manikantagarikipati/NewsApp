package com.geekmk.newsapp.data.source.local

import android.arch.persistence.room.*
import com.geekmk.newsapp.data.model.NewsArticle
import io.reactivex.Single


@Dao
interface NewsArticleDao {

    @Query("SELECT * FROM newsarticle")
    fun queryNewsArticles(): Single<List<NewsArticle>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertNewsArticle(newsArticle: NewsArticle)

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertAllNewsArticles(newsArticles: List<NewsArticle>)

    @Query("DELETE FROM newsarticle")
    fun deleteAllArticles()
}