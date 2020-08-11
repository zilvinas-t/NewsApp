package com.zilvinas.newsapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Article(
    @SerializedName("urlToImage")
    val image: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val dateTime: Date?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("url")
    val url: String?
)