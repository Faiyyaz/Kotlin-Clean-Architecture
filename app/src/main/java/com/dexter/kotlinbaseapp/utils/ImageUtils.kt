package com.dexter.kotlinbaseapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dexter.kotlinbaseapp.R

/**
 * Created by Admin on 06-12-2017.
 */

object ImageUtils {

    fun setImage(context: Context, imageView: ImageView, url: String) {
        val requestOptions = RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).circleCrop()
        Glide.with(context).asBitmap().load(url).apply(requestOptions).into(imageView)
    }

    fun setRectangleImage(context: Context, imageView: ImageView, url: String) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
        Glide.with(context).asBitmap().load(url).apply(requestOptions).into(imageView)
    }

    fun getOriginalPoster(url: String): String? {
        var url = url
        return if (!android.text.TextUtils.isEmpty(url)) {
            val originalUrl = url.split(Constants.token.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            url = originalUrl[0] + originalUrl[1]
            url
        } else {
            url
        }
    }
}
