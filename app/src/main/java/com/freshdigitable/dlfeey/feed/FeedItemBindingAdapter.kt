package com.freshdigitable.dlfeey.feed

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rometools.rome.feed.synd.SyndEntry

@BindingAdapter("imageUrl")
fun ImageView.loadImageUrl(entry: SyndEntry?) {
    val url = entry?.foreignMarkup?.firstOrNull { it.qualifiedName == "hatena:imageurl" }
        ?.content?.get(0)?.value
    if (url == null) {
        Glide.with(this)
            .clear(this)
    } else {
        Glide.with(this)
            .load(url)
            .into(this)
    }
}
