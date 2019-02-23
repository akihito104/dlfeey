package com.freshdigitable.dlfeye

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import okhttp3.*
import java.io.IOException

class FeedRepository {
    private val client = OkHttpClient()
    private val _feed = MutableLiveData<SyndFeed>()
    val feed: LiveData<SyndFeed> = _feed

    fun load(url: String) {
        val request = Request.Builder()
            .url(url)
            .method("GET", null)
            .build()
        client.newCall(request)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body() ?: return
                    val input = SyndFeedInput()
                    val feed = input.build(body.charStream())
                    _feed.postValue(feed)
                }

                override fun onFailure(call: Call, e: IOException) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
    }
}
