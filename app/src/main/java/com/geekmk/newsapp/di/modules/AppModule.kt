package com.geekmk.newsapp.di.modules

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.geekmk.newsapp.App
import com.geekmk.newsapp.data.source.local.Database
import com.geekmk.newsapp.data.source.local.NewsArticleDao
import com.geekmk.newsapp.ui.newslist.NewsArticlesViewModelFactory
import com.geekmk.newsapp.utils.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideNewsArticleDataBase(app: App): Database =
        Room.databaseBuilder(app, Database::class.java, "newsapp_db").build()


    @Provides
    @Singleton
    fun provideNewsArticleDao(database: Database): NewsArticleDao = database.newsArticlesDao()



    @Provides
    @Singleton
    fun provideNewsArticleViewModelFactory(
        factory: NewsArticlesViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideUtils(): Utils = Utils(app)

}