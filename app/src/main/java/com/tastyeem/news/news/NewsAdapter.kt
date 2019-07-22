package com.tastyeem.news.news

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tastyeem.news.R
import kotlinx.android.synthetic.main.card_item_news.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import com.arellomobile.mvp.MvpPresenter
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tastyeem.news.swipeLayout.OnSwipeTouchListener
import com.tastyeem.news.swipeLayout.SwipeLayout
import java.lang.Exception
import javax.sql.DataSource


class NewsAdapter(private val news : ArrayList<News>, private val context : Context, private val presenter: PAcontract.presenter) : RecyclerView.Adapter<NewsAdapter.NewsHolder>()  {

    private var lastPosition: Int = -1
    private var firstTime = true
    private val STATE_CREATED = 1
    private val STATE_SWIPED_LEFT = 2
    private val STATE_SWIPED_RIGHT = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_news, parent, false)
        val vh = NewsHolder(v)
        vh.itemView.setOnTouchListener(object : OnSwipeTouchListener() {
            override fun onClick() {
                super.onClick()
                val uri = Uri.parse(news.get(vh.adapterPosition).clickUrl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }

            override fun onSwipeLeft() {
                setAnimation(vh.itemView, vh.adapterPosition, STATE_SWIPED_LEFT)
                deleteItem(vh.adapterPosition, vh)
            }

            override fun onSwipeRight() {
                setAnimation(vh.itemView, vh.adapterPosition, STATE_SWIPED_RIGHT)
                deleteItem(vh.adapterPosition, vh)
            }
        })
        return vh
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.itemView.Author.setText(news.get(position).author)

        var imageUrl = news.get(position).urlToImage
        if((!(imageUrl.contains("http")))&&(!(imageUrl.contains("https"))))imageUrl = "https:" + imageUrl
        Glide.with(context).load(imageUrl).into(holder.itemView.PreviewImage)

        holder.itemView.Title.setText(news.get(position).title)
        setAnimation(holder.itemView, position, STATE_CREATED)
    }

    private fun setAnimation(viewToAnimate: View, position: Int, state: Int) {
        if(state==STATE_CREATED){
            if (position > lastPosition) {
                val animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                viewToAnimate.startAnimation(animation)
                lastPosition = position
            }
        }
        if(state==STATE_SWIPED_LEFT){
            val animation = AnimationUtils.loadAnimation(context, R.anim.to_right)
            viewToAnimate.startAnimation(animation)
        }
        if(state==STATE_SWIPED_RIGHT){
            val animation = AnimationUtils.loadAnimation(context, R.anim.to_left)
            viewToAnimate.startAnimation(animation)
        }
    }

    fun deleteItem(position: Int, holder: NewsHolder) {
        val newPosition = holder.getAdapterPosition()
        news.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(newPosition, news.size)
        if(news.size==0){
            firstTime=true
            presenter.RecyclerIsEmpty()
        }
    }


    class NewsHolder(v: View) : RecyclerView.ViewHolder(v)
}

