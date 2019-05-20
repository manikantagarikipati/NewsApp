package com.geekmk.newsapp.di.modules

import com.geekmk.newsapp.ui.newslist.NewsListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuildersModule{

    @ContributesAndroidInjector
    abstract fun contributeNewsListActivity(): NewsListActivity
}