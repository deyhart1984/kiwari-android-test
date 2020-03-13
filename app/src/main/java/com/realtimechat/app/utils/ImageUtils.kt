package com.realtimechat.app.utils

import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.realtimechat.app.R

object ImageUtils {

    fun RequestOption(): RequestOptions {
        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.no_picture)
            .error(R.drawable.no_picture)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .priority(Priority.HIGH)
        return options
    }
}