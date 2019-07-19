package com.tastyeem.news.mvp.interfaces

import android.content.Context
import android.graphics.Bitmap
import com.tastyeem.news.newsApi.NewsObj
/*
interface to working presenter with Model
 */
interface MainPMcontract {
    interface presenter{
        fun newsDownloaded(news: NewsObj)
        fun networkError()

    }
    interface model{
        fun downloadNews(context : Context)
    }
}