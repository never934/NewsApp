package com.tastyeem.news.newsApi

import com.google.gson.annotations.SerializedName

data class Source (
    @SerializedName("id")
    val id : String,
    @SerializedName("name")
    val author : String
)