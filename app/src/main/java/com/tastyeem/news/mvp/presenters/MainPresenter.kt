package com.tastyeem.news.mvp.presenters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.tastyeem.news.mvp.interfaces.MainPMcontract
import com.tastyeem.news.mvp.models.MainModel
import com.tastyeem.news.mvp.views.MainView
import com.tastyeem.news.news.NewsAdapter
import com.tastyeem.news.news.PAcontract
import com.tastyeem.news.newsApi.NewsObj
import com.tastyeem.news.rx.NewsConvayor
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(), MainPMcontract.presenter, PAcontract.presenter {

    private lateinit var model : MainModel
    private lateinit var MainContext : Context
    private lateinit var RecyclerNewsAdapter : NewsAdapter

    fun init(context: Context){
        model =  MainModel(this)
        MainContext = context
    }

    fun getNews(context: Context){
        model.downloadNews(context)
    }

    @SuppressLint("CheckResult")
    override fun newsDownloaded(news: NewsObj) {
        NewsConvayor.formatList(news).subscribeBy(
            onNext = {newsList ->
                val adapter : NewsAdapter = NewsAdapter(newsList, MainContext, this)
                RecyclerNewsAdapter = adapter
                viewState.showNews(adapter)},
            onError = { error -> Log.e("NewsFormater", error.message)},
            onComplete = { })
    }

    override fun RecyclerIsEmpty() {
        viewState.showEndLabel()
    }

    override fun networkError() {
        viewState.showError()
    }


}