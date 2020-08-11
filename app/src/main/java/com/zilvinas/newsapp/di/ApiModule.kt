package com.zilvinas.newsapp.di

import com.zilvinas.newsapp.model.ArticlesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private var baseUrl = "http://newsapi.org/v2/"

    @Provides
    fun provideArticlesApi() : ArticlesApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticlesApi::class.java)
    }
}