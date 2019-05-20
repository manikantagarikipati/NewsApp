package com.geekmk.newsapp

import android.app.Activity
import android.app.Application
import com.geekmk.newsapp.di.component.DaggerAppComponent
import com.geekmk.newsapp.di.modules.AppModule
import com.geekmk.newsapp.di.modules.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class App : Application(), HasActivityInjector {


    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(BuildConfig.BASE_URL))
            .bindApplication(this)
            .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector


}