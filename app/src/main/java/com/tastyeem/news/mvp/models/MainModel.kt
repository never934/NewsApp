package com.tastyeem.news.mvp.models

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.tastyeem.news.R
import com.tastyeem.news.mvp.interfaces.MainPMcontract
import com.tastyeem.news.mvp.views.MainView
import com.tastyeem.news.newsApi.NewsApi
import com.tastyeem.news.newsApi.NewsObj
import com.tastyeem.news.rx.NewsConvayor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainModel(val presenter: MainPMcontract.presenter) : MainModelContract, MainPMcontract.model {

    @SuppressLint("CheckResult")
    override fun downloadNews(context : Context) {
        val api = NewsApi.create()
        val key = context.getString(R.string.api_key)
        api.getNews("ru", key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess =
                { t ->
                    val result = t.totalResults
                    Log.e("Retrofit", "success" + result)
                    presenter.newsDownloaded(t)
                },
                onError =
                { e -> Log.e("Retrofit", "error" + e.message); presenter.networkError() })
    }
}