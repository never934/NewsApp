package com.tastyeem.news.mvp.activities

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View.*
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.tastyeem.news.R
import com.tastyeem.news.mvp.presenters.MainPresenter
import com.tastyeem.news.mvp.views.MainView
import com.tastyeem.news.news.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter
    lateinit var context: Context
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        prepareActivity()
        initActivity()
    }

    private fun prepareActivity(){
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initActivity(){
        linearLayoutManager = LinearLayoutManager(context)
        presenter.init(context)
        showLoading()
        presenter.getNews(context)
        MainLayout.setOnRefreshListener {
            presenter.getNews(context);
            errorCloud.visibility = INVISIBLE;
            EndLabel.visibility = INVISIBLE
            }
    }

    override fun showNews(adapter : NewsAdapter) {
        progressBar.visibility = INVISIBLE
        MainLayout.isRefreshing = false
        NewsList.visibility = VISIBLE
        NewsList.layoutManager = linearLayoutManager
        NewsList.adapter = adapter
    }

    override fun showError() {
        if(NewsList.adapter==null){
            errorCloud.visibility = VISIBLE
        }
        progressBar.visibility = INVISIBLE
        MainLayout.isRefreshing = false
        Toast.makeText(applicationContext, context.getString(R.string.error_downloading), Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        NewsList.visibility = INVISIBLE
        progressBar.visibility = VISIBLE
    }

    override fun showEndLabel() {
        EndLabel.visibility = VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        EndLabel.startAnimation(animation)
    }

}
