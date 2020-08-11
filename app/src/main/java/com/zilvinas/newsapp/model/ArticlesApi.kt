package com.zilvinas.newsapp.model

import io.reactivex.Single
import retrofit2.http.GET

interface ArticlesApi {

    @GET("top-headlines?country=us&apiKey=57a79eac5a8f44efa2bd3408139b83f3")
    fun getArticles(): Single<ApiData>
}