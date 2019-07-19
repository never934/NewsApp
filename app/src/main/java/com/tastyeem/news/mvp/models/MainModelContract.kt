package com.tastyeem.news.mvp.models

import android.content.Context
import com.tastyeem.news.newsApi.NewsObj

interface MainModelContract {
    fun downloadNews(context: Context)
}