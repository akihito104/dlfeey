package com.freshdigitable.dlfeey.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepository @Inject constructor(
    private val client: OkHttpClient
){
    private val feedMap: MutableMap<String, LiveData<SyndFeed?>> = mutableMapOf()

    fun getFeed(url: String): LiveData<SyndFeed?> {
        return feedMap[url] ?: load(url)
    }

    private fun load(url: String): LiveData<SyndFeed?> {
        val feedSource = MutableLiveData<SyndFeed?>()
        feedMap[url] = feedSource

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
                    feedSource.postValue(feed)
                }

                override fun onFailure(call: Call, e: IOException) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        return feedSource
    }
}
