package com.tastyeem.news.mvp.views

import com.arellomobile.mvp.MvpView
import com.tastyeem.news.news.NewsAdapter

interface MainView: MvpView {
    fun showNews(adapter : NewsAdapter)
    fun showError()
    fun showLoading()
    fun showEndLabel()
}