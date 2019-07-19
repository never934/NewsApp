package com.tastyeem.news.rx

import android.content.Context
import android.util.Log
import com.tastyeem.news.news.News
import com.tastyeem.news.newsApi.NewsObj
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.tastyeem.news.R
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class NewsConvayor {
    companion object {
        fun formatList(news : NewsObj): Observable<ArrayList<News>> {
            val observable: Observable<ArrayList<News>>
            observable = Observable.create(object : ObservableOnSubscribe<ArrayList<News>> {
                override fun subscribe(emitter: ObservableEmitter<ArrayList<News>>) {
                    try{
                    var number = 0
                    val NewsList:ArrayList<News> = ArrayList()
                    while(number<news.articles.size-1) {
                        var urlToImage = news.articles.get(number).urlToImage
                        if((urlToImage==null)){
                            urlToImage=""
                        }
                                val obj: News = News(
                                news.articles.get(number).title,
                                news.articles.get(number).source.author,
                                news.articles.get(number).url,
                                urlToImage
                            )
                            NewsList.add(obj)
                            Log.e("Par", news.articles.get(number).title)
                            number++
                        }
                        NewsList.shuffle()
                        emitter.onNext(NewsList)
                    }catch(e: Exception){emitter.onError(e)}
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            return observable
        }
    }
}