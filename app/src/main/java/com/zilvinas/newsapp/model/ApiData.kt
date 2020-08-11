package com.zilvinas.newsapp.model

import com.google.gson.annotations.SerializedName

data class ApiData(
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("articles")
    val articles: ArrayList<Article>?
)