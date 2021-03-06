package com.zubair.kotlinjetpack.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zubair.kotlinjetpack.R

val PERMISSION_SEND_SMS = 234

fun getProgressDrawable(context: Context): CircularProgressDrawable =
    CircularProgressDrawable(context).apply {
        strokeWidth =10f
        centerRadius = 50f
        start()
    }

//if you want to use glide
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions().placeholder(progressDrawable).error(R.drawable.ic_dog_breed)
    Glide.with(context).setDefaultRequestOptions(options).load(uri).into(this)
}

//This is a binding adapter for custom attribute
@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?){
    view.loadImage(url, getProgressDrawable(view.context))
}