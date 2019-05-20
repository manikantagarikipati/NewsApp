package com.geekmk.newsapp.di.component

import com.geekmk.newsapp.App
import com.geekmk.newsapp.di.modules.AppModule
import com.geekmk.newsapp.di.modules.BuildersModule
import com.geekmk.newsapp.di.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton
import dagger.BindsInstance


@Singleton
@Component(
    modules = [AndroidInjectionModule::class, BuildersModule::class, AppModule::class, NetworkModule::class]
)

interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        fun appModule(module: AppModule): Builder
        fun networkModule(module: NetworkModule): Builder
        fun build():AppComponent

        @BindsInstance
        fun bindApplication(app: App):Builder

    }
}